package s2.sample.did.domain.features

import s2.sample.did.domain.DidEvent
import s2.sample.did.domain.DidId
import s2.sample.did.domain.DidInitCommand
import s2.sample.did.domain.DidState
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

@Serializable
@JsExport
@JsName("DidCreateCommand")
open class DidCreateCommand(
	val id: DidId,
) : DidInitCommand

@Serializable
@JsExport
@JsName("DidCreatedEvent")
class DidCreatedEvent(
	override val id: String,
	override val type: DidState,
): DidEvent
