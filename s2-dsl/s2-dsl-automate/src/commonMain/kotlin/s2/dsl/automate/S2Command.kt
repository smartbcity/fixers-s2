package s2.dsl.automate

import f2.dsl.cqrs.Command
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

expect interface S2InitCommand: Command

expect interface S2Command<ID>: Command {
	@JsName("id")
	val id: ID
}
