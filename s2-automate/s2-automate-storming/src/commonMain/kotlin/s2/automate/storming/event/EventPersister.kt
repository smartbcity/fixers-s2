package s2.automate.storming.event

import f2.dsl.cqrs.Event
import kotlinx.coroutines.flow.Flow
import s2.dsl.automate.model.WithS2Id

interface EventPersister<EVENT, ID> where
EVENT : WithS2Id<ID>,
EVENT : Event
{
	suspend fun load(id: ID): Flow<EVENT>
	suspend fun persist(event: EVENT): EVENT
}
