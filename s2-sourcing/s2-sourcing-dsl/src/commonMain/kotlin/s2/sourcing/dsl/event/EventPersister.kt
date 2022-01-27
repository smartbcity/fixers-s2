package s2.sourcing.dsl.event

import f2.dsl.cqrs.Event
import kotlinx.coroutines.flow.Flow

interface EventPersister<EVENT, ID> where
//EVENT : WithS2Id<ID>,
EVENT : Event
{
	suspend fun load(id: ID): Flow<EVENT>
	suspend fun persist(event: EVENT): EVENT
}
