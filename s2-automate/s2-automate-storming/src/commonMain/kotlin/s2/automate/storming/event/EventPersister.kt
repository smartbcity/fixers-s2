package s2.automate.storming.event

import kotlinx.coroutines.flow.Flow

interface EventPersister<EVENT, ID> {
	suspend fun load(id: ID): Flow<EVENT>
	suspend fun persist(event: EVENT): EVENT
}
