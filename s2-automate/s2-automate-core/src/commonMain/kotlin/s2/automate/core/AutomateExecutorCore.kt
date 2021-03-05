package s2.automate.core

import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.automate.core.appevent.*
import s2.automate.core.appevent.publisher.AutomateAppEventPublisher
import s2.automate.core.context.AutomateContext
import s2.automate.core.context.InitTransitionContext
import s2.automate.core.context.TransitionContext
import s2.automate.core.error.AutomateException
import s2.automate.core.error.ERROR_ENTITY_NOT_FOUND
import s2.automate.core.error.asException
import s2.automate.core.guard.GuardExecutorImpl
import s2.automate.core.persist.AutotmatePersister

open class AutomateExecutorCore<STATE, ID, ENTITY>(
	private val automateContext: AutomateContext<STATE, ID, ENTITY>,
	private val guardExecutor: GuardExecutorImpl<STATE, ID, ENTITY>,
	private val persister: AutotmatePersister<STATE, ID, ENTITY>,
	private val publisher: AutomateAppEventPublisher<STATE, ID, ENTITY>,
) : AutomateExecutor<STATE, ID, ENTITY>
where STATE : S2State, ENTITY : WithS2State<STATE>, ENTITY : WithS2Id<ID> {

	override suspend fun create(command: S2InitCommand, to: STATE, buildEntity: suspend () -> ENTITY): ENTITY {
		try {
			val initTransitionContext = initTransitionContext(command, to)
			guardExecutor.evaluateInit(initTransitionContext)
			val entity = buildEntity()
			sentEndCreateEvent(to, command, entity)
			persister.persist(initTransitionContext, entity)
			return entity
		} catch (e: AutomateException) {
			throw e
		} catch (e: Exception) {
			publisher.automateTransitionError(
				AutomateTransitionError(
					command = command,
					to = to,
					exception = e
				)
			)
			throw e
		}
	}

	override suspend fun doTransition(command: S2Command<ID>, to: STATE, exec: suspend ENTITY.() -> ENTITY): ENTITY {
		return doTransitionWithResult(command, to) {
			val entityMutated = this.exec()
			Pair(entityMutated, entityMutated)
		}
	}

	override suspend fun <RESULT> doTransitionWithResult(
		command: S2Command<ID>,
		to: STATE,
		exec: suspend ENTITY.() -> Pair<ENTITY, RESULT>,
	): RESULT {
		try {
			val (entity, transitionContext) = loadTransitionContext(command, to)
			guardExecutor.evaluateTransition(transitionContext)

			val (entityMutated, result) = exec(entity)

			persister.persist(transitionContext, entityMutated)
			sendEndDoTransitionEvent(to, transitionContext.from, command, entity)

			return result
		} catch (e: AutomateException) {
			throw e
		} catch (e: Exception) {
			publisher.automateTransitionError(
				AutomateTransitionError(
					command = command,
					to = to,
					exception = e
				)
			)
			throw e
		}
	}

	private fun initTransitionContext(
		command: S2InitCommand,
		to: STATE
	): InitTransitionContext<STATE, ID, ENTITY> {
		val initTransitionContext = InitTransitionContext(
			automateContext = automateContext,
			command = command,
			to = to,
		)
		publisher.automateInitTransitionStarted(
			AutomateInitTransitionStarted(
				to = to,
				command = command
			)
		)
		return initTransitionContext
	}

	private fun sentEndCreateEvent(to: STATE, command: S2InitCommand, entity: ENTITY) {
		publisher.automateInitTransitionEnded(
			AutomateInitTransitionEnded(
				to = to,
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
				state = to
			)
		)
	}

	private fun sendEndDoTransitionEvent(
		to: STATE,
		fromState: STATE,
		command: S2Command<ID>,
		entity: ENTITY
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
		to: STATE
	): Pair<ENTITY, TransitionContext<STATE, ID, ENTITY>> {
		val entity = persister.load(id = command.id) ?: throw ERROR_ENTITY_NOT_FOUND(command.id.toString()).asException()
		val transitionContext = TransitionContext(
			automateContext = automateContext,
			from = entity.s2State(),
			command = command,
			entity = entity,
			to = to,
		)
		publisher.automateTransitionStarted(
			AutomateTransitionStarted(
				to = to,
				from = entity.s2State(),
				command = command
			)
		)
		return Pair(entity, transitionContext)
	}

}