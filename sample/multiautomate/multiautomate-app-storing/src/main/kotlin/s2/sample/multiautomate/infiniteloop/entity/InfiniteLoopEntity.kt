package s2.sample.multiautomate.infiniteloop.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import s2.dsl.automate.model.WithS2IdAndStatus
import s2.sample.multiautomate.infiniteloop.InfiniteLoopId
import s2.sample.multiautomate.infiniteloop.InfiniteLoopState
import java.util.UUID
import s2.spring.sourcing.data.entity.EntityBase

@Document
data class InfiniteLoopEntity(
	@Id
	val id: InfiniteLoopId = UUID.randomUUID().toString(),
	val step: Int,
	var state: Int,
) : EntityBase(), WithS2IdAndStatus<InfiniteLoopId, InfiniteLoopState> {
	override fun s2Id() = id
	override fun s2State() = InfiniteLoopState(state)
}
