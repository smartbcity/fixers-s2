package s2.sample.did.domain

import s2.dsl.automate.builder.s2
import s2.sample.did.domain.features.DidAddPublicKeyCommand
import s2.sample.did.domain.features.DidCreateCommand
import s2.sample.did.domain.features.DidRevokeCommand
import s2.sample.did.domain.features.DidRevokePublicKeyCommand
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName
import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2Event
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2Role
import s2.dsl.automate.S2State

typealias DidId = String

@JsExport
@JsName("didS2")
fun didS2(): S2Automate {
	return s2 {
		name = "DidS2"
		init<DidCreateCommand> {
			to = DidState.Created()
			role = DidRole.Admin()
		}
		transaction<DidAddPublicKeyCommand> {
			from = DidState.Created()
			to = DidState.Actived()
			role = DidRole.Owner()
		}
		transaction<DidRevokeCommand> {
			from = DidState.Actived()
			to = DidState.Actived()
			role = DidRole.Owner()
		}
		transaction<DidRevokePublicKeyCommand> {
			from = DidState.Actived()
			to = DidState.Revoked()
			role = DidRole.Owner()
		}
	}
}

@Serializable
@JsExport
@JsName("DidRole")
sealed class DidRole : S2Role {
	class Admin : DidRole()
	class Owner : DidRole()

	override fun toString(): String {
		return this::class.simpleName!!
	}
}

@Serializable
@JsExport
@JsName("DidState")
open class DidState(
	override val position: Int,
) : S2State {
	@Serializable
	open class Created : DidState(0)

	@Serializable
	open class Actived : DidState(1)

	@Serializable
	open class Revoked : DidState(2)

	override fun toString(): String {
		return this::class.simpleName!!
	}
}

// @JsExport
@JsName("DidInitCommand")
interface DidInitCommand : S2InitCommand

// @JsExport
@JsName("DidCommand")
interface DidCommand : S2Command<DidId>

// @JsExport
@JsName("DidEvent")
interface DidEvent : S2Event<DidState, DidId>
