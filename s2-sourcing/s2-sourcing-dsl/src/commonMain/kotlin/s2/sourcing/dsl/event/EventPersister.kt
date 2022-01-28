package s2.sourcing.dsl.event

import f2.dsl.cqrs.Event
import kotlinx.coroutines.flow.Flow
import s2.dsl.automate.Evt

interface EventPersister<EVENT, ID> where
EVENT : Evt
{
	suspend fun load(id: ID): Flow<EVENT>
	suspend fun persist(event: EVENT): EVENT
}
