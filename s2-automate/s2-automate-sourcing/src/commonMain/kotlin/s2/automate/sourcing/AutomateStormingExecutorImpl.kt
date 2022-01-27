package s2.automate.sourcing

import f2.dsl.cqrs.Event
import kotlinx.coroutines.flow.flowOf
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
import s2.sourcing.dsl.event.EventPersister
import s2.sourcing.dsl.event.SourcingProjectionBuilder
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.automate.sourcing.automate.S2StormingAutomate
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

open class AutomateStormingExecutorImpl<STATE, ID, ENTITY, EVENT>(
	private val automateContext: AutomateContext<STATE, ID, ENTITY, S2StormingAutomate<ID>>,
	private val guardExecutor: GuardExecutorImpl<STATE, ID, ENTITY, S2StormingAutomate<ID>>,
	private val publisher: AutomateEventPublisher<STATE, ID, ENTITY, S2StormingAutomate<ID>>,
	private val projectionBuilder: SourcingProjectionBuilder<ENTITY, EVENT, ID>,
	private val eventStore: EventPersister<EVENT, ID>,
): AutomateStormingExecutor<ENTITY, STATE, EVENT, ID> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
EVENT: Event,
EVENT :  WithS2Id<ID> {


	@Suppress("ThrowsCount")
	override suspend fun <EVENTD : EVENT> create(command: S2InitCommand, toEvent: suspend () -> EVENTD): EVENTD {
		try {
			val initTransitionContext = initTransitionContext(command)
			guardExecutor.evaluateInit(initTransitionContext)
			val event = toEvent()
			val entity = projectionBuilder.replay(flowOf(event))
				?: throw ERROR_ENTITY_NOT_FOUND(event.s2Id().toString()).asException()
			persist(command, entity, event)
			sentEndCreateEvent(command, entity)
			return event
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

	private suspend fun persist(command: S2InitCommand, entity: ENTITY, event: EVENT) {
		val initTransitionPersistContext = InitTransitionAppliedContext(
			automateContext = automateContext,
			command = command,
			entity = entity
		)
		guardExecutor.verifyInitTransition(initTransitionPersistContext)
		eventStore.persist(event)
//		persister.persist(initTransitionPersistContext)
	}

	@Suppress("ThrowsCount")
	override suspend fun <EVENTD : EVENT> doTransition(
		command: S2Command<ID>,
		exec: suspend ENTITY.() -> EVENTD,
	): EVENTD {
		try {
			val transitionContext = loadTransitionContext(command)
			guardExecutor.evaluateTransition(transitionContext)
			val fromState = transitionContext.entity.s2State()

			val event = exec(transitionContext.entity)
			val entityMutated = projectionBuilder.replayAndEvolve(command.id, flowOf(event))
				?: throw ERROR_ENTITY_NOT_FOUND(event.s2Id().toString()).asException()
			persist(fromState, command, entityMutated, event)
			sendEndDoTransitionEvent(entityMutated.s2State(), transitionContext.from, command, transitionContext.entity)
			return event
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

	private suspend fun persist(fromState: STATE, command: S2Command<ID>, entityMutated: ENTITY, event: EVENT) {
		val transitionPersistContext = TransitionAppliedContext(
			automateContext = automateContext,
			from = fromState,
			command = command,
			entity = entityMutated
		)
		guardExecutor.verifyTransition(transitionPersistContext)
		eventStore.persist(event)
//		persister.persist(transitionPersistContext)
	}

	private fun initTransitionContext(
		command: S2InitCommand,
	): InitTransitionContext<STATE, ID, ENTITY, S2StormingAutomate<ID>> {
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
	): TransitionContext<STATE, ID, ENTITY, S2StormingAutomate<ID>> {
		val entity = projectionBuilder.replay(command.id)
			?: throw ERROR_ENTITY_NOT_FOUND(command.id.toString()).asException()
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
		return transitionContext
	}
}
