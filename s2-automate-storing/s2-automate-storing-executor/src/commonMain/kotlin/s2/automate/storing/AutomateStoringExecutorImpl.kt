package s2.automate.storing

import s2.automate.core.appevent.AutomateInitTransitionEnded
import s2.automate.core.appevent.AutomateInitTransitionStarted
import s2.automate.core.appevent.AutomateSessionStarted
import s2.automate.core.appevent.AutomateSessionStopped
import s2.automate.core.appevent.AutomateStateEntered
import s2.automate.core.appevent.AutomateStateExited
import s2.automate.core.appevent.AutomateTransitionEnded
import s2.automate.core.appevent.AutomateTransitionError
import s2.automate.core.appevent.AutomateTransitionStarted
import s2.automate.core.appevent.publisher.AutomateEventPublisher
import s2.automate.core.context.AutomateContext
import s2.automate.core.context.InitTransitionAppliedContext
import s2.automate.core.context.InitTransitionContext
import s2.automate.core.context.TransitionAppliedContext
import s2.automate.core.context.TransitionContext
import s2.automate.core.error.AutomateException
import s2.automate.core.error.ERROR_ENTITY_NOT_FOUND
import s2.automate.core.error.asException
import s2.automate.core.guard.GuardExecutorImpl
import s2.automate.core.persist.AutomatePersister
import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

open class AutomateStoringExecutorImpl<STATE, ID, ENTITY>(
	private val automateContext: AutomateContext<S2Automate>,
	private val guardExecutor: GuardExecutorImpl<STATE, ID, ENTITY, S2Automate>,
	private val persister: AutomatePersister<STATE, ID, ENTITY, S2Automate>,
	private val publisher: AutomateEventPublisher<STATE, ID, ENTITY, S2Automate>,
) : AutomateStoringExecutor<STATE, ENTITY,  ID>
		where STATE : S2State, ENTITY : WithS2State<STATE>, ENTITY : WithS2Id<ID> {

	override suspend fun <EVENT_OUT : ENTITY> create(command: S2InitCommand, decide: suspend () -> EVENT_OUT): EVENT_OUT {
		try {
			val initTransitionContext = initTransitionContext(command)
			guardExecutor.evaluateInit(initTransitionContext)
			val entity = decide()
			persist(command, entity)
			sentEndCreateEvent(command, entity)
			return entity
		} catch (e: AutomateException) {
			throw e
		} catch (e: Exception) {
			publisher.automateTransitionError(
				AutomateTransitionError(
					msg = command,
					exception = e
				)
			)
			throw e
		}
	}

	private suspend fun persist(command: S2InitCommand, entity: ENTITY) {
		val initTransitionPersistContext = InitTransitionAppliedContext(
			automateContext = automateContext,
			msg = command,
			entity = entity
		)
		guardExecutor.verifyInitTransition(initTransitionPersistContext)
		persister.persist(initTransitionPersistContext)
	}

	override suspend fun <EVENT_OUT : ENTITY> doTransition(command: S2Command<ID>, exec: suspend ENTITY.() -> EVENT_OUT): EVENT_OUT {
		return doTransitionWithResult(command) {
			val entityMutated = this.exec()
			Pair(entityMutated, entityMutated)
		}
	}

	suspend fun <RESULT> doTransitionWithResult(
		command: S2Command<ID>,
		exec: suspend ENTITY.() -> Pair<ENTITY, RESULT>,
	): RESULT {
		try {
			val (entity, transitionContext) = loadTransitionContext(command)
			guardExecutor.evaluateTransition(transitionContext)
			val fromState = entity.s2State()
			val (entityMutated, result) = exec(entity)
			persist(fromState, command, entityMutated)
			sendEndDoTransitionEvent(entityMutated.s2State(), transitionContext.from, command, entity)

			return result
		} catch (e: AutomateException) {
			throw e
		} catch (e: Exception) {
			publisher.automateTransitionError(
				AutomateTransitionError(
					msg = command,
					exception = e
				)
			)
			throw e
		}
	}

	private suspend fun persist(fromState: STATE, command: S2Command<ID>, entityMutated: ENTITY) {
		val transitionPersistContext = TransitionAppliedContext(
			automateContext = automateContext,
			from = fromState,
			msg = command,
			entity = entityMutated
		)
		guardExecutor.verifyTransition(transitionPersistContext)
		persister.persist(transitionPersistContext)
	}

	private fun initTransitionContext(
		command: S2InitCommand,
	): InitTransitionContext<S2Automate> {
		val initTransitionContext = InitTransitionContext(
			automateContext = automateContext,
			msg = command,
		)
		publisher.automateInitTransitionStarted(
			AutomateInitTransitionStarted(
				msg = command
			)
		)
		return initTransitionContext
	}

	private fun sentEndCreateEvent(command: S2InitCommand, entity: ENTITY) {
		publisher.automateInitTransitionEnded(
			AutomateInitTransitionEnded(
				to = entity.s2State(),
				msg = command,
				entity = entity
			)
		)
		publisher.automateSessionStarted(
			AutomateSessionStarted(
				automate = automateContext.automate
			)
		)
		publisher.automateStateEntered(
			AutomateStateEntered(
				state = entity.s2State(),
			)
		)
	}

	private fun sendEndDoTransitionEvent(
		to: STATE,
		fromState: STATE,
		command: S2Command<ID>,
		entity: ENTITY,
	) {
		publisher.automateTransitionEnded(
			AutomateTransitionEnded(
				to = to,
				from = fromState,
				msg = command,
				entity = entity
			)
		)
		if (automateContext.automate.isSameState(fromState, to)) {
			publisher.automateStateExited(
				AutomateStateExited(
					state = entity.s2State()
				)
			)
		}
		if (automateContext.automate.isFinalState(to)) {
			publisher.automateSessionStopped(
				AutomateSessionStopped(
					automate = automateContext.automate
				)
			)
		}
	}

	private suspend fun loadTransitionContext(
		command: S2Command<ID>,
	): Pair<ENTITY, TransitionContext<STATE, ID, ENTITY, S2Automate>> {
		val entity =
			persister.load(automateContext, id = command.id) ?: throw ERROR_ENTITY_NOT_FOUND(command.id.toString()).asException()
		val transitionContext = TransitionContext(
			automateContext = automateContext,
			from = entity.s2State(),
			msg = command,
			entity = entity,
		)
		publisher.automateTransitionStarted(
			AutomateTransitionStarted(
				from = entity.s2State(),
				msg = command
			)
		)
		return Pair(entity, transitionContext)
	}
}
