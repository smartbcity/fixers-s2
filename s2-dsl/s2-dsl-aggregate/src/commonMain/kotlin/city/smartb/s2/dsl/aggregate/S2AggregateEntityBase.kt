package city.smartb.s2.dsl.aggregate

import city.smartb.s2.dsl.aggregate.entity.S2Repository
import city.smartb.s2.dsl.aggregate.entity.WithS2Id
import city.smartb.s2.dsl.aggregate.entity.WithS2State
import city.smartb.s2.dsl.aggregate.event.EntitySnapshotAppEvent
import city.smartb.s2.dsl.aggregate.event.EventPublisher
import city.smartb.s2.dsl.automate.S2Automate
import city.smartb.s2.dsl.automate.S2Command
import city.smartb.s2.dsl.automate.S2InitCommand
import city.smartb.s2.dsl.automate.S2State
import kotlin.js.JsName

//@JsExport
@JsName("S2AggregateEntityBase")
open class S2AggregateEntityBase<STATE, ID, ENTITY>(
		private val s2: S2Automate,
		private val repository: S2Repository<ENTITY, ID>,
		private val publisher: EventPublisher,
): S2AggregateEntity<STATE, ID, ENTITY>
		where STATE: S2State, ENTITY : WithS2State<STATE>, ENTITY: WithS2Id<ID> {

	private val snapshotAggregate = S2AggregateSnapshotBase(s2, repository, publisher)

	override suspend fun create(command: S2InitCommand, buildEntity: suspend () -> ENTITY): ENTITY {
		val entity = snapshotAggregate.create(command, buildEntity)
		repository.save(entity)
		return entity
	}

	override suspend fun doTransition(command: S2Command<ID>, exec: suspend ENTITY.() -> ENTITY): ENTITY {
		val state = snapshotAggregate.doTransition(command, exec)
		val entityUpdated = repository.save(state)
		publisher.publish(EntitySnapshotAppEvent(entityUpdated))
		return state
	}

}