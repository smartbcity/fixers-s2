package s2.dsl.automate

import f2.dsl.cqrs.Command

@JsExport
@JsName("S2InitCommand")
actual external interface S2InitCommand : Command

@JsExport
@JsName("S2Command")
actual external interface S2Command<ID> : Command {
	@JsName("id")
	actual val id: ID
}