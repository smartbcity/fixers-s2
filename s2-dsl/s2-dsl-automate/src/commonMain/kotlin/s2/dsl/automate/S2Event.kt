package s2.dsl.automate

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import kotlin.js.JsExport
import kotlin.js.JsName

expect interface S2Event<out STATE : S2State, ID> : Event {
	val id: ID
	val type: STATE
}

@JsExport
@JsName("S2EventSuccess")
class S2EventSuccess<out STATE : S2State, out COMMAND : Command, ID>(
	@JsName("id")
	val id: ID,
	val type: COMMAND,
	@JsName("from")
	val from: STATE,
	@JsName("to")
	val to: STATE,
) : Event

@JsExport
@JsName("S2EventError")
class S2EventError<out STATE : S2State, out COMMAND : Command, ID>(
	@JsName("id")
	val id: ID,
	val type: COMMAND,
	@JsName("from")
	val from: STATE,
	@JsName("to")
	val to: STATE,
	val error: S2Error
) : Event
