package s2.dsl.automate

import f2.dsl.cqrs.Event

@JsExport
@JsName("S2Event")
actual external interface S2Event<out STATE : S2State, ID> : Evt,  WithId<ID> {
	actual override val id: ID

	@JsName("type")
	actual val type: STATE
}
