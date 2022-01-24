package s2.spring.storming.event

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringDataEventRepository<EVENT, ID>: CoroutineCrudRepository<EventStorming<EVENT, ID>, String> {
	suspend fun findAllByObjId(objId: ID) : Flow<EventStorming<EVENT, ID>>
}
