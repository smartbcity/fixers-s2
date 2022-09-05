package s2.automate.storing

import s2.automate.core.S2AutomateExecutor
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2State
import kotlin.js.JsName

@JsName("AutomateSourcingExecutor")
interface AutomateStoringExecutor<STATE, ENTITY, ID>
	: S2AutomateExecutor<ENTITY, STATE, ID, ENTITY>
where
ENTITY : WithS2State<STATE>,
STATE : S2State {
	override suspend fun <ENTITY_OUT : ENTITY> create(command: S2InitCommand, decide: suspend () -> ENTITY_OUT): ENTITY_OUT
	override suspend fun <ENTITY_OUT : ENTITY> doTransition(command: S2Command<ID>, exec: suspend (ENTITY) -> ENTITY_OUT): ENTITY_OUT
}
