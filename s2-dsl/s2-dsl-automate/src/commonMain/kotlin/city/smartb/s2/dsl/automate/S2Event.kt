package city.smartb.s2.dsl.automate

import city.smartb.f2.dsl.cqrs.Command
import city.smartb.f2.dsl.cqrs.Event
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("S2Event")
interface S2Event<out ACTION : S2State, ID> : Event<ACTION> {
	@JsName("id")
	val id: ID
}

@JsExport
@JsName("S2EventSuccess")
class S2EventSuccess<out STATE : S2State, out COMMAND : Command, ID>(
	@JsName("id")
	val id: ID,
	override val type: COMMAND,
	@JsName("from")
	val from: STATE,
	@JsName("to")
	val to: STATE,
) : Event<COMMAND>

@JsExport
@JsName("S2EventError")
class S2EventError<out STATE : S2State, out COMMAND : Command, ID>(
	@JsName("id")
	val id: ID,
	override val type: COMMAND,
	@JsName("from")
	val from: STATE,
	@JsName("to")
	val to: STATE,
) : Event<Command>
