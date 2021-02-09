package city.smartb.s2.spring.aggregate.io

import city.smartb.s2.dsl.aggregate.entity.S2Repository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Component

@Component
class AggregateRepository<ENTITY, ID>(
		val repository: ReactiveCrudRepository<ENTITY, ID>,
): S2Repository<ENTITY, ID> {

	override suspend fun findById(id: ID): ENTITY? {
		return repository.findById(id).awaitFirstOrNull()
	}

	override suspend fun save(entity: ENTITY): ENTITY {
		return repository.save(entity).awaitSingle()
	}
}