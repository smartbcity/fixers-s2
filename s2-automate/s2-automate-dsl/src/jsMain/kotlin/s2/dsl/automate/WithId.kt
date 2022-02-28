package s2.dsl.automate

@JsExport
@JsName("MessageWithId")
actual external interface WithId<ID> {
	@JsName("id")
	actual val id: ID
}
