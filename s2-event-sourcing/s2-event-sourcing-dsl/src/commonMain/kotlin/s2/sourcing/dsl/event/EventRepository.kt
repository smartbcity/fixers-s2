package s2.sourcing.dsl.event

import kotlinx.coroutines.flow.Flow
import s2.dsl.automate.Evt

interface EventRepository<EVENT: Evt, ID> {
	suspend fun load(id: ID): Flow<EVENT>
	suspend fun loadAll(): Flow<EVENT>
	suspend fun persist(event: EVENT): EVENT
}
