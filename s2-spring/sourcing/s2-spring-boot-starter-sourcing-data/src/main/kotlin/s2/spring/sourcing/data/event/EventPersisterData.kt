package s2.spring.sourcing.data.event

import java.util.UUID
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import s2.dsl.automate.Evt
import s2.dsl.automate.model.WithS2Id
import s2.sourcing.dsl.event.EventRepository

class EventPersisterData<EVENT, ID>(
	private val eventRepository: SpringDataEventRepository<EVENT, ID>,
	private val eventType: KClass<EVENT>
) : EventRepository<EVENT, ID> where
EVENT: Evt,
EVENT: WithS2Id<ID>
{

	override suspend fun load(id: ID): Flow<EVENT> {
		return eventRepository.findAllByObjId(id).map { it.event }
	}

	override suspend fun loadAll(): Flow<EVENT> {
		return eventRepository.findAll().map { it.event }.filter(eventType::isInstance)
	}

	override suspend fun persist(event: EVENT): EVENT {
		return eventRepository.save(
			EventSourcing(
				id = UUID.randomUUID().toString(),
				objId = event.s2Id(),
				event = event
			)
		).event
	}
}
