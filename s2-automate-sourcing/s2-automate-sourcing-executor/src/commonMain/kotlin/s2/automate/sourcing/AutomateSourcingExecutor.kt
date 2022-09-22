package s2.automate.sourcing

import s2.automate.core.S2AutomateExecutor
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2State
import kotlin.js.JsName

@JsName("AutomateSourcingExecutor")
interface AutomateSourcingExecutor<STATE, EVENT, ENTITY, ID>
	: S2AutomateExecutor<ENTITY, STATE, ID, EVENT>
where
ENTITY : WithS2State<STATE>,
STATE : S2State {
	override suspend fun <EVENT_OUT : EVENT> create(command: S2InitCommand, decide: suspend () -> EVENT_OUT): EVENT_OUT
	suspend fun <EVENT_OUT : EVENT> doTransition(command: S2Command<ID>, exec: suspend (ENTITY) -> EVENT_OUT): EVENT_OUT
}
