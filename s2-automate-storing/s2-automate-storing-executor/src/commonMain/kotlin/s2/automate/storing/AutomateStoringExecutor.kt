package s2.automate.storing

import s2.automate.core.S2AutomateExecutor
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2State
import kotlin.js.JsName

@JsName("AutomateSourcingExecutor")
interface AutomateStoringExecutor<STATE, ENTITY, ID, EVENT>
	: S2AutomateExecutor<ENTITY, STATE, ID, ENTITY>
where
ENTITY : WithS2State<STATE>,
STATE : S2State {
	suspend fun <ENTITY_OUT: ENTITY, EVENT_OUT : EVENT> create(
		command: S2InitCommand, decide: suspend () -> Pair<ENTITY_OUT, EVENT_OUT>
	): Pair<ENTITY_OUT, EVENT_OUT>

	suspend fun <ENTITY_OUT: ENTITY, EVENT_OUT : EVENT> doTransition(
		command: S2Command<ID>,
		exec: suspend ENTITY.() -> Pair<ENTITY_OUT, EVENT_OUT>
	): Pair<ENTITY_OUT, EVENT_OUT>
}
