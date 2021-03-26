package s2.spring.automate.executor

import s2.automate.core.AutomateExecutor
import s2.automate.core.appevent.publisher.AppEventPublisher
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

open class S2AutomateExecutorSpring<STATE, ID, ENTITY> : S2AutomateExecutor<STATE, ID, ENTITY> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {

	private lateinit var automateExecutor: AutomateExecutor<STATE, ID, ENTITY>
	private lateinit var publisher: AppEventPublisher

	fun withContext(automateExecutor: AutomateExecutor<STATE, ID, ENTITY>, publisher: AppEventPublisher) {
		this.automateExecutor = automateExecutor
		this.publisher = publisher
	}

	override suspend fun <EVENT> createWithEvent(
		command: S2InitCommand,
		to: STATE,
		buildEvent: suspend ENTITY.() -> EVENT,
		buildEntity: suspend () -> ENTITY
	): EVENT {
		val entity = automateExecutor.create(command, to, buildEntity)
		val event = buildEvent(entity)
		publisher.publish(event)
		return event
	}

	override suspend fun <EVENT> createWithEvent(
		command: S2InitCommand,
		to: STATE,
		build: suspend () -> Pair<ENTITY, EVENT>
	): EVENT {
		val (entity, domainEvent) =  build()
		automateExecutor.create(command, to, { entity })
		publisher.publish(domainEvent)
		return domainEvent
	}

	override suspend fun <T> doTransition(
		command: S2Command<ID>,
		to: STATE,
		save: Boolean,
		exec: suspend ENTITY.() -> Pair<ENTITY, T>
	): T {
		return doTransition(command.id, command, to, save, exec)
	}

	override suspend fun <T> doTransition(
		id: ID,
		command: S2Command<ID>,
		to: STATE,
		save: Boolean,
		exec: suspend ENTITY.() -> Pair<ENTITY, T>
	): T {
		val event: T = automateExecutor.doTransitionWithResult(command, to, exec)
		publisher.publish(event)
		return event
	}
}