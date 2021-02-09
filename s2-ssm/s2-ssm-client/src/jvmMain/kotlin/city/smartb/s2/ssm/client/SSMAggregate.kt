package city.smartb.s2.ssm.client

import city.smartb.s2.dsl.aggregate.S2Aggregate
import city.smartb.s2.dsl.aggregate.entity.S2Repository
import city.smartb.s2.dsl.aggregate.entity.WithS2IdAndStatus
import city.smartb.s2.dsl.aggregate.event.EventPublisher
import city.smartb.s2.dsl.aggregate.exception.EntityNotFoundException
import city.smartb.s2.dsl.automate.S2Automate
import city.smartb.s2.dsl.automate.S2Command
import city.smartb.s2.dsl.automate.S2InitCommand
import city.smartb.s2.dsl.automate.S2State
import kotlinx.coroutines.future.await
import org.civis.blockchain.ssm.client.SsmClient
import org.civis.blockchain.ssm.client.domain.Context
import org.civis.blockchain.ssm.client.domain.Session
import org.civis.blockchain.ssm.client.domain.Signer

class SSMAggregate<STATE : S2State, ID, ENTITY : WithS2IdAndStatus<ID, STATE>>(
	private val ssmClient: SsmClient,
	private val signer: Signer,
	private val automate: S2Automate,
	private val jsonMapper: JsonMapper,
	private val publisher: EventPublisher,
	private val repository: S2Repository<ENTITY, ID>,
): S2Aggregate<STATE, ID, ENTITY>{
	suspend fun create(
		command: S2InitCommand,
		buildEntity: suspend () -> ENTITY
	): ENTITY {
		val entity = buildEntity()
		return startSession(entity)
	}

	override suspend fun <EVENT> createWithEvent(command: S2InitCommand, buildEvent: suspend ENTITY.() -> EVENT, buildEntity: suspend () -> ENTITY): EVENT {
		val saved = create(command, buildEntity)
		val event = buildEvent(saved)
		publisher.publish(event)
		return event
	}

	override suspend fun <EVENT> createWithEvent(command: S2InitCommand, build: suspend () -> Pair<ENTITY, EVENT>): EVENT {
		val (entity, event) = build()
		startSession(entity)
		return event
	}

	override suspend fun <T> doTransition(command: S2Command<ID>, save: Boolean, exec: suspend ENTITY.() -> T): T {
		return doTransition(command.id, command, save, exec)
	}

	override suspend fun <T> doTransition(id: ID, command: S2Command<ID>, save: Boolean, exec: suspend ENTITY.() -> T): T {
		val entity = getEntity(id)
		val sessionId = id.toString()
		val session = ssmClient.getSession(sessionId).await().orElseThrow {
			EntityNotFoundException(sessionId)
		}
		val toSave = entity.exec()
		val json = jsonMapper.encodeToString(toSave)
		val context = Context(sessionId, json, session.iteration+1)
		ssmClient.perform(signer, command::class.simpleName, context)
		return toSave
	}

	private suspend fun startSession(entity: ENTITY): ENTITY {
		val json = jsonMapper.encodeToString(entity)
		val session = Session(automate.name, entity.s2Id().toString(), json, emptyMap())
		ssmClient.start(signer, session).await()
		return entity
	}


	suspend fun getEntity(id : ID) : ENTITY {
		return repository.findById(id) ?: throw EntityNotFoundException(id.toString())
	}
}
