package s2.sample.did.domain

import s2.dsl.automate.builder.s2
import s2.sample.did.domain.features.DidAddPublicKeyCommand
import s2.sample.did.domain.features.DidCreateCommand
import s2.sample.did.domain.features.DidRevokeCommand
import s2.sample.did.domain.features.DidRevokePublicKeyCommand
import kotlinx.serialization.Serializable
import s2.dsl.automate.*
import kotlin.js.JsExport
import kotlin.js.JsName

typealias DidId = String

@JsExport
@JsName("didS2")
fun didS2(): S2Automate {
	return s2<DidId, DidState> {
		name = "DidS2"
		init<DidInitCommand> {
			to = DidState.Created()
			role = DidRole.Admin()
			cmd = DidCreateCommand::class
		}
		transaction<DidCommand> {
			from = DidState.Created()
			to = DidState.Actived()
			role = DidRole.Owner()
			cmd = DidAddPublicKeyCommand::class
		}
		transaction<DidCommand> {
			from = DidState.Actived()
			to = DidState.Actived()
			role = DidRole.Owner()
			cmd = DidAddPublicKeyCommand::class
		}
		transaction<DidCommand> {
			from = DidState.Actived()
			to = DidState.Actived()
			role = DidRole.Owner()
			cmd = DidRevokeCommand::class
		}
		transaction<DidCommand> {
			from = DidState.Actived()
			to = DidState.Revoked()
			role = DidRole.Owner()
			cmd = DidRevokePublicKeyCommand::class
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
	override val position: Int
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

@JsExport
@JsName("DidInitCommand")
interface DidInitCommand : S2InitCommand

@JsExport
@JsName("DidCommand")
interface DidCommand: S2Command<DidId>

@JsExport
@JsName("DidEvent")
interface DidEvent : S2Event<DidState, DidId>