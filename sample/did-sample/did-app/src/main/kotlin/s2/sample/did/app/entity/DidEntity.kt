package s2.sample.did.app.entity

import s2.sample.did.domain.DidId
import s2.sample.did.domain.DidState
import f2.spring.data.entity.EntityBase
import java.util.UUID
import s2.dsl.automate.model.WithS2IdAndStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class DidEntity(
	@Id
	val id: DidId = UUID.randomUUID().toString(),
	val publicKeys: MutableList<String> = mutableListOf(),
	var state: Int,
) : EntityBase(), WithS2IdAndStatus<DidId, DidState> {
	override fun s2Id() = id
	override fun s2State() = DidState(state)
}
