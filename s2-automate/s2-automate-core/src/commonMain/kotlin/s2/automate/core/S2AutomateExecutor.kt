package s2.automate.core

import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2State
import kotlin.js.JsName

@JsName("S2AutomateExecutor")
interface S2AutomateExecutor<ENTITY, STATE, ID, TO> where
ENTITY : WithS2State<STATE>,
STATE : S2State {
//	suspend fun <EVENT_OUT : TO> create(command: S2InitCommand, decide: suspend () -> EVENT_OUT): EVENT_OUT
//	suspend fun <EVENT_OUT : TO> doTransition(command: S2Command<ID>, exec: suspend (ENTITY) -> EVENT_OUT): EVENT_OUT
}
