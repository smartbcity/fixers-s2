package s2.dsl.automate

import f2.dsl.cqrs.Command
import kotlin.js.JsName

expect interface S2InitCommand : Command

expect interface S2Command<ID> : Command {
	@JsName("id")
	val id: ID
}
