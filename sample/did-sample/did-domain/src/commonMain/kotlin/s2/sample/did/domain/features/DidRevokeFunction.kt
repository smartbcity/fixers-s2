package s2.sample.did.domain.features

import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import s2.sample.did.domain.DidCommand
import s2.sample.did.domain.DidEvent
import s2.sample.did.domain.DidId
import s2.sample.did.domain.DidState
import kotlin.js.JsExport
import kotlin.js.JsName

typealias DidRevokeCommandFunction = F2Function<DidRevokeCommand, DidRevokedEvent>

@JsExport
@JsName("DidRevokeCommandPayload")
interface DidRevokeCommandPayload {
	val id: DidId
}

@Serializable
@JsExport
@JsName("DidRevokeCommand")
class DidRevokeCommand(
	override val id: DidId,
) : DidCommand, DidRevokeCommandPayload

@Serializable
@JsExport
@JsName("DidRevokedEvent")
class DidRevokedEvent(
	override val id: DidId,
	override val type: DidState,
) : DidEvent
