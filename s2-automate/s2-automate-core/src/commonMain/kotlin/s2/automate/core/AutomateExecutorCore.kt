package s2.automate.core

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
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

open class AutomateExecutorCore<STATE, ID, ENTITY>(
	private val automateContext: AutomateContext<STATE, ID, ENTITY>,
	private val guardExecutor: GuardExecutorImpl<STATE, ID, ENTITY>,
	private val persister: AutomatePersister<STATE, ID, ENTITY>,
	private val publisher: AutomateEventPublisher<STATE, ID, ENTITY>,
) : AutomateExecutor<STATE, ID, ENTITY>
		where STATE : S2State, ENTITY : WithS2State<STATE>, ENTITY : WithS2Id<ID> {

	override suspend fun create(command: S2InitCommand, buildEntity: suspend () -> ENTITY): ENTITY {
		try {
			val initTransitionContext = initTransitionContext(command)
			guardExecutor.evaluateInit(initTransitionContext)
			val entity = buildEntity()
			persist(command, entity)
			sentEndCreateEvent(command, entity)
			return entity
		} catch (e: AutomateException) {
			throw e
		} catch (e: Exception) {
			publisher.automateTransitionError(
				AutomateTransitionError(
					command = command,
					exception = e
				)
			)
			throw e
		}
	}

	private suspend fun persist(command: S2InitCommand, entity: ENTITY) {
		val initTransitionPersistContext = InitTransitionAppliedContext(
			automateContext = automateContext,
			command = command,
			entity = entity
		)
		guardExecutor.verifyInitTransition(initTransitionPersistContext)
		persister.persist(initTransitionPersistContext)
	}

	override suspend fun doTransition(command: S2Command<ID>, exec: suspend ENTITY.() -> ENTITY): ENTITY {
		return doTransitionWithResult(command) {
			val entityMutated = this.exec()
			Pair(entityMutated, entityMutated)
		}
	}

	override suspend fun <RESULT> doTransitionWithResult(
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
					command = command,
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
			command = command,
			entity = entityMutated
		)
		guardExecutor.verifyTransition(transitionPersistContext)
		persister.persist(transitionPersistContext)
	}

	private fun initTransitionContext(
		command: S2InitCommand,
	): InitTransitionContext<STATE, ID, ENTITY> {
		val initTransitionContext = InitTransitionContext(
			automateContext = automateContext,
			command = command,
		)
		publisher.automateInitTransitionStarted(
			AutomateInitTransitionStarted(
				command = command
			)
		)
		return initTransitionContext
	}

	private fun sentEndCreateEvent(command: S2InitCommand, entity: ENTITY) {
		publisher.automateInitTransitionEnded(
			AutomateInitTransitionEnded(
				to = entity.s2State(),
				command = command,
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
				command = command,
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
	): Pair<ENTITY, TransitionContext<STATE, ID, ENTITY>> {
		val entity =
			persister.load(automateContext, id = command.id) ?: throw ERROR_ENTITY_NOT_FOUND(command.id.toString()).asException()
		val transitionContext = TransitionContext(
			automateContext = automateContext,
			from = entity.s2State(),
			command = command,
			entity = entity,
		)
		publisher.automateTransitionStarted(
			AutomateTransitionStarted(
				from = entity.s2State(),
				command = command
			)
		)
		return Pair(entity, transitionContext)
	}
}
