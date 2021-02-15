package city.smartb.s2.dsl.aggregate

import city.smartb.s2.dsl.aggregate.entity.FindByIdFetcher
import city.smartb.s2.dsl.aggregate.entity.WithS2Id
import city.smartb.s2.dsl.aggregate.entity.WithS2State
import city.smartb.s2.dsl.aggregate.event.CommandSnapAppEvent
import city.smartb.s2.dsl.aggregate.event.StateSnapAppEvent
import city.smartb.s2.dsl.aggregate.event.EventPublisher
import city.smartb.s2.dsl.aggregate.exception.EntityNotFoundException
import city.smartb.s2.dsl.aggregate.extention.checkTransition
import city.smartb.s2.dsl.automate.S2Automate
import city.smartb.s2.dsl.automate.S2Command
import city.smartb.s2.dsl.automate.S2InitCommand
import city.smartb.s2.dsl.automate.S2State

/**
 * S2AggregateSnapshotBase doesn't store state in the database
 * It just emit CommandInitSnapshotAppEvent or CommandSnapshotAppEvent when receiving the command
 * and EntitySnapshotAppEvent when the state has been updated
 */
open class S2AggregateSnapshotBase<STATE, ID, ENTITY>(
	private val s2: S2Automate,
	private val fetcher: FindByIdFetcher<ENTITY, ID>,
	private val publisher: EventPublisher
): S2AggregateEntity<STATE, ID, ENTITY>
		where STATE: S2State, ENTITY : WithS2State<STATE>, ENTITY: WithS2Id<ID> {

	override suspend fun create(command: S2InitCommand, buildEntity: suspend () -> ENTITY): ENTITY {
		publisher.publish(CommandSnapAppEvent(command))
		val entity = buildEntity()
		publisher.publish(StateSnapAppEvent(entity))
		return entity
	}

	private suspend fun getEntity(id : ID) : ENTITY {
		return fetcher.findById(id) ?: throw EntityNotFoundException(id.toString())
	}

	override suspend fun doTransition(command: S2Command<ID>, exec: suspend ENTITY.() -> ENTITY): ENTITY {
		//CommandSnapEvent
		publisher.publish(CommandSnapAppEvent(command))
		val entity = getEntity(command.id)
		//ContextEntrySnapEvent
		command.checkTransition(entity, s2)
		//ContextGuardSnapEvent
		val entityUpdated = exec(entity)
		//ContextExitSnapEvent
		publisher.publish(StateSnapAppEvent(entityUpdated))
		return entityUpdated
	}

}