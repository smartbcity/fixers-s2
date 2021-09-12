package s2.sample.did.domain.features

import f2.dsl.fnc.F2Function
import s2.sample.did.domain.DidCommand
import s2.sample.did.domain.DidEvent
import s2.sample.did.domain.DidId
import s2.sample.did.domain.DidState
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

typealias DidRevokePublicKeyCommandFunction = F2Function<DidRevokePublicKeyCommand, DidRevokedPublicKeyEvent>

// @JsExport
@JsName("DidRevokePublicKeyCommandPayload")
interface DidRevokePublicKeyCommandPayload {
	val id: DidId
}

@Serializable
// @JsExport
@JsName("DidRevokePublicKeyCommand")
class DidRevokePublicKeyCommand(
	override val id: DidId,
) : DidCommand, DidRevokePublicKeyCommandPayload

@Serializable
@JsExport
@JsName("DidRevokedPublicKeyFncEvent")
class DidRevokedPublicKeyEvent(
	override val type: DidState,
	override val id: DidId,
) : DidEvent
