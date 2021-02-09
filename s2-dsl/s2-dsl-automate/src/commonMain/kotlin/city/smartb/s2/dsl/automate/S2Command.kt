package city.smartb.s2.dsl.automate

import city.smartb.f2.dsl.cqrs.Command
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

@Serializable
@JsExport
@JsName("S2InitCommand")
interface S2InitCommand: Command

@Serializable
@JsExport
@JsName("S2Command")
interface S2Command<ID>: Command {
	@JsName("id")
	val id: ID
}
