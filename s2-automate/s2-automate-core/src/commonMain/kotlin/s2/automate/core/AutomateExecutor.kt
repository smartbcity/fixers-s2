package s2.automate.core

import s2.dsl.automate.model.WithS2State
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import kotlin.js.JsName

@JsName("AutomateExecutor")
interface AutomateExecutor<STATE: S2State, ID, ENTITY: WithS2State<STATE>> {

	suspend fun create(command: S2InitCommand, to: STATE, buildEntity: suspend () -> ENTITY): ENTITY

	suspend fun doTransition(command: S2Command<ID>, to: STATE, exec: suspend ENTITY.() -> ENTITY): ENTITY

	suspend fun <RESULT> doTransitionWithResult(command: S2Command<ID>, to: STATE, exec: suspend ENTITY.() -> Pair<ENTITY, RESULT>): RESULT

}