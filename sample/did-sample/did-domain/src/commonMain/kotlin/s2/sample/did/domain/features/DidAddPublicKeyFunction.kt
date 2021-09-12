package s2.sample.did.domain.features

import f2.dsl.fnc.F2Function
import s2.sample.did.domain.DidCommand
import s2.sample.did.domain.DidEvent
import s2.sample.did.domain.DidId
import s2.sample.did.domain.DidState
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

typealias DidAddPublicKeyCommandFunction = F2Function<DidAddPublicKeyCommand, DidAddPublicKeyEvent>

/**
 * Test comment DidAddPublicKeyCommandPayload
 */
@JsExport
@JsName("DidAddPublicKeyCommandPayload")
class DidAddPublicKeyCommandPayload(
	val id: DidId,
)

/**
 * Test comment DidAddPublicKeyCommand
 */
@Serializable
@JsExport
@JsName("DidAddPublicKeyCommand")
class DidAddPublicKeyCommand(
	override val id: DidId,
	val publicKey: String,
) : DidCommand

/**
 * Test comment DidAddPublicKeyEvent
 */
@Serializable
@JsExport
@JsName("DidAddPublicKeyEvent")
open class DidAddPublicKeyEvent(
	override val id: DidId,
	override val type: DidState,
) : DidEvent
