package city.smartb.s2.dsl.aggregate

import city.smartb.s2.dsl.aggregate.entity.S2Repository
import city.smartb.s2.dsl.aggregate.entity.WithS2Id
import city.smartb.s2.dsl.aggregate.entity.WithS2State
import city.smartb.s2.dsl.aggregate.event.EventPublisher
import city.smartb.s2.dsl.aggregate.exception.EntityNotFoundException
import city.smartb.s2.dsl.aggregate.extention.checkTransition
import city.smartb.s2.dsl.automate.S2Automate
import city.smartb.s2.dsl.automate.S2Command
import city.smartb.s2.dsl.automate.S2InitCommand
import city.smartb.s2.dsl.automate.S2State
import kotlin.js.JsName


@JsName("S2AggregateBase")
open class S2AggregateBase<STATE, ID, ENTITY>(
	private val s2: S2Automate,
	private val repository: S2Repository<ENTITY, ID>,
	private val publisher: EventPublisher
): S2Aggregate<STATE, ID, ENTITY>
		where STATE: S2State, ENTITY : WithS2State<STATE>, ENTITY: WithS2Id<ID> {

	override suspend fun <EVENT> createWithEvent(command: S2InitCommand, buildEvent: suspend ENTITY.() -> EVENT, buildEntity: suspend () -> ENTITY): EVENT {
		val entityAggregate = S2AggregateEntityBase(s2, repository, publisher)
		val saved = entityAggregate.create(command, buildEntity)
		val event = buildEvent(saved)
		publisher.publish(event)
		return event
	}

	override suspend fun <EVENT> createWithEvent(command: S2InitCommand, build: suspend () -> Pair<ENTITY, EVENT>): EVENT {
		publisher.publish(command)
		val (entity, event) = build()
		repository.save(entity)
		publisher.publish(event)
		return event
	}

	private suspend fun getEntity(id : ID) : ENTITY {
		return repository.findById(id) ?: throw EntityNotFoundException(id.toString())
	}

	override suspend fun <T> doTransition(command: S2Command<ID>, save: Boolean, exec: suspend ENTITY.() -> T): T {
		return doTransition(command.id, command, save, exec)
	}

	override suspend fun <T> doTransition(id: ID, command: S2Command<ID>, save: Boolean, exec: suspend ENTITY.() -> T): T {
		publisher.publish(command)
		val entity = getEntity(id)
		command.checkTransition(entity, s2)
		val event = exec(entity)
		if(save) {
			repository.save(entity)
		}
		publisher.publish(event)
		return event
	}



}