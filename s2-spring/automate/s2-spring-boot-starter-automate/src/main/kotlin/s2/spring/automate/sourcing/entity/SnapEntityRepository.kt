package s2.spring.automate.sourcing.entity

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

@Repository
interface SnapEntityRepository<STATE, ID, ENTITY> : ReactiveCrudRepository<StormingSnapEntity<STATE, ID, ENTITY>, ID>
		where STATE : S2State, ENTITY : WithS2State<STATE>, ENTITY : WithS2Id<ID> {
	fun findByEntityId(id: ID): Flux<StormingSnapEntity<STATE, ID, ENTITY>>
}
