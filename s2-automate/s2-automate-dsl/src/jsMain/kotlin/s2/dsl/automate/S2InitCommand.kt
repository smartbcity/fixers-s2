package s2.dsl.automate

@JsExport
@JsName("S2InitCommand")
actual external interface S2InitCommand : Cmd

@JsExport
@JsName("S2Command")
actual external interface S2Command<ID> : Cmd, WithId<ID> {
	actual override val id: ID
}
