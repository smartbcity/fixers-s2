package city.smartb.s2.spring.aggregate.snap.entity

import city.smartb.s2.dsl.aggregate.entity.WithS2Id
import city.smartb.s2.dsl.aggregate.entity.WithS2State
import city.smartb.s2.dsl.automate.S2State
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface SnapEntityRepository<STATE, ID, ENTITY>: ReactiveCrudRepository<SnapEntity<STATE, ID, ENTITY>, ID>
		where STATE: S2State, ENTITY : WithS2State<STATE>, ENTITY: WithS2Id<ID> {
	fun findByEntityId(id: ID): Flux<SnapEntity<STATE, ID, ENTITY>>
}

