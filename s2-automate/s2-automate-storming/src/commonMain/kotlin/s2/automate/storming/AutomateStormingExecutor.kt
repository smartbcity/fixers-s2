package s2.automate.storming

import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2State
import kotlin.js.JsName

@JsName("AutomateStormingExecutor")
interface AutomateStormingExecutor<STATE : S2State, ID, ENTITY : WithS2State<STATE>, EVENT> {

	suspend fun <EVENTD : EVENT> init(command: S2InitCommand, buildEvent: suspend () -> EVENTD): EVENTD
	suspend fun  <EVENTD : EVENT> transition(command: S2Command<ID>, exec: suspend ENTITY.() -> EVENTD): EVENTD

}
