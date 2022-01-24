package s2.spring.storming.event

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import s2.automate.storming.event.EventPersister
import s2.dsl.automate.WithId
import java.util.UUID

class EventStoreSpringData<EVENT: WithId<ID>, ID>(
	private val eventRepository: SpringDataEventRepository<EVENT, ID>,
) : EventPersister<EVENT, ID> {
	override suspend fun load(id: ID): Flow<EVENT> {
		return eventRepository.findAllByObjId(id).map { it.event }
	}

	override suspend fun persist(event: EVENT): EVENT {
		return eventRepository.save(
			EventStorming(
				id = UUID.randomUUID().toString(),
				objId = event.id,
				event = event
			)
		).event
	}
}
