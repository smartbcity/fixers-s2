package s2.spring.storming.data.event

import f2.dsl.cqrs.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import s2.automate.storming.event.EventPersister
import s2.dsl.automate.model.WithS2Id
import java.util.UUID

class EventPersisterData<EVENT, ID>(
	private val eventRepository: SpringDataEventRepository<EVENT, ID>,
) : EventPersister<EVENT, ID> where
EVENT: Event,
EVENT: WithS2Id<ID>
{

	override suspend fun load(id: ID): Flow<EVENT> {
		return eventRepository.findAllByObjId(id).map { it.event }
	}

	override suspend fun persist(event: EVENT): EVENT {
		return eventRepository.save(
			EventStorming(
				id = UUID.randomUUID().toString(),
				objId = event.s2Id(),
				event = event
			)
		).event
	}
}
