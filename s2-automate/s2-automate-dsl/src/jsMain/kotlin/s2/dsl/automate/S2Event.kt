package s2.dsl.automate

import f2.dsl.cqrs.Event

@JsExport
@JsName("S2Event")
actual external interface S2Event<out STATE : S2State, ID> : Event,  WithId<ID>, Msg {
	actual override val id: ID

	@JsName("type")
	actual val type: STATE
}
