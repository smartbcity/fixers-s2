package s2.sample.multiautomate.endable.entity

import f2.spring.data.entity.EntityBase
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import s2.dsl.automate.model.WithS2IdAndStatus
import s2.sample.multiautomate.endable.EndableLoopId
import s2.sample.multiautomate.endable.EndableLoopState
import java.util.UUID

@Document
data class EndableLoopEntity(
	@Id
	val id: EndableLoopId = UUID.randomUUID().toString(),
	var step: Int,
	var state: Int,
) : EntityBase(), WithS2IdAndStatus<EndableLoopId, EndableLoopState> {
	override fun s2Id() = id
	override fun s2State() = EndableLoopState(state)
}
