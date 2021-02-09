package city.smartb.s2.spring.aggregate.snap.io

import city.smartb.s2.dsl.aggregate.entity.S2Repository
import city.smartb.s2.dsl.aggregate.entity.WithS2Id
import city.smartb.s2.dsl.aggregate.entity.WithS2State
import city.smartb.s2.dsl.aggregate.event.StateSnapAppEvent
import city.smartb.s2.dsl.automate.S2State
import city.smartb.s2.spring.aggregate.snap.entity.SnapEntity
import city.smartb.s2.spring.aggregate.snap.entity.SnapEntityRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Component

@Component
class AggregateRepository<STATE, ID, ENTITY>(
	val repository: SnapEntityRepository<STATE, ID, ENTITY>,
): S2Repository<ENTITY, ID>
		where STATE: S2State, ENTITY : WithS2State<STATE>, ENTITY: WithS2Id<ID> {

	override suspend fun findById(id: ID): ENTITY? {
		println("[AggregateRepository] Start findById in database => ${id}")
		return repository.findById(id).map { it.event.entity }.awaitFirstOrNull()
	}

	override suspend fun save(entity: ENTITY): ENTITY {
		println("[AggregateRepository] Start saving in database => ${entity}")
		val event = StateSnapAppEvent(
			entity
		)
		val snap = SnapEntity(event.entity.s2Id(), event.entity.s2Id(),event)
		return repository.save(snap).awaitFirst().event.entity
	}

}