package s2.automate.sourcing

import s2.automate.core.S2Executor
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2State
import kotlin.js.JsName

@JsName("AutomateStormingExecutor")
interface AutomateStormingExecutor<ENTITY , STATE, EVENT, ID>
	: S2Executor<ENTITY, STATE, ID, EVENT>
where
ENTITY : WithS2State<STATE>,
STATE : S2State
{

	override suspend fun <EVENTD : EVENT> create(command: S2InitCommand, buildEvent: suspend () -> EVENTD): EVENTD

	override suspend fun  <EVENTD : EVENT> doTransition(command: S2Command<ID>, exec: suspend (ENTITY) -> EVENTD): EVENTD
}
