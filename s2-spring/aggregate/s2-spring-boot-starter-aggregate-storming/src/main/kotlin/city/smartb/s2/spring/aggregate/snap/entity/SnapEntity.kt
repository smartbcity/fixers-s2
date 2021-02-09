package city.smartb.s2.spring.aggregate.snap.entity

import city.smartb.s2.dsl.aggregate.entity.WithS2Id
import city.smartb.s2.dsl.aggregate.entity.WithS2State
import city.smartb.s2.dsl.aggregate.event.StateSnapAppEvent
import city.smartb.s2.dsl.automate.S2State
import org.springframework.data.annotation.Id
import javax.persistence.Entity

typealias SnapId = String

@Entity
class SnapEntity<STATE, ID, ENTITY>(
	@Id
	val id: ID,
	val entityId: ID,
	val event: StateSnapAppEvent<STATE, ID, ENTITY>
) where STATE : S2State, ENTITY : WithS2State<STATE>, ENTITY : WithS2Id<ID>