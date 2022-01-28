package s2.spring.automate.sourcing.entity

import org.springframework.data.annotation.Id
import s2.automate.core.appevent.AppEvent
import s2.dsl.automate.Msg
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import javax.persistence.Entity

@Entity
class StormingSnapEntity<STATE, ID, ENTITY>(
	@Id
	val id: ID,
	val entityId: ID,
	val event: StormingSnapTransaction<STATE, ID, ENTITY>,
) where STATE : S2State, ENTITY : WithS2State<STATE>, ENTITY : WithS2Id<ID>

class StormingSnapTransaction<STATE, ID, ENTITY>(
	val from: S2State?,
	val to: S2State,
	val command: Msg,
	val entity: ENTITY,
) : AppEvent
		where STATE : S2State, ENTITY : WithS2State<STATE>, ENTITY : WithS2Id<ID>
