package s2.spring.automate.executor

import s2.automate.core.appevent.publisher.AppEventPublisher
import s2.automate.storing.AutomateStoringExecutorImpl
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

open class S2AutomateExecutorSpring<STATE, ID, ENTITY> : S2AutomateStoringExecutor<STATE, ID, ENTITY> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {

	private lateinit var automateExecutor: AutomateStoringExecutorImpl<STATE, ID, ENTITY>
	private lateinit var publisher: AppEventPublisher

	fun withContext(automateExecutor: AutomateStoringExecutorImpl<STATE, ID, ENTITY>, publisher: AppEventPublisher) {
		this.automateExecutor = automateExecutor
		this.publisher = publisher
	}

	override suspend fun <EVENT> createWithEvent(
		command: S2InitCommand,
		buildEvent: suspend ENTITY.() -> EVENT,
		buildEntity: suspend () -> ENTITY,
	): EVENT {
		val entity = automateExecutor.create(command, buildEntity)
		val event = buildEvent(entity)
		publisher.publish(event)
		return event
	}

	override suspend fun <EVENT> createWithEvent(
		command: S2InitCommand,
		build: suspend () -> Pair<ENTITY, EVENT>,
	): EVENT {
		val (entity, domainEvent) = build()
		automateExecutor.create(command, { entity })
		publisher.publish(domainEvent)
		return domainEvent
	}

	override suspend fun <T> doTransition(
		command: S2Command<ID>,
		exec: suspend ENTITY.() -> Pair<ENTITY, T>,
	): T {
		return doTransition(command.id, command, exec)
	}

	override suspend fun <T> doTransition(
		id: ID,
		command: S2Command<ID>,
		exec: suspend ENTITY.() -> Pair<ENTITY, T>,
	): T {
		val event: T = automateExecutor.doTransitionWithResult(command, exec)
		publisher.publish(event)
		return event
	}
}
