package s2.spring.automate.executor

import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2State

interface S2AutomateStoringExecutor<STATE : S2State, ID, ENTITY : WithS2State<STATE>> {

	suspend fun <EVENT> createWithEvent(
		command: S2InitCommand,
		buildEvent: suspend ENTITY.() -> EVENT,
		buildEntity: suspend () -> ENTITY,
	): EVENT

	suspend fun <EVENT> createWithEvent(command: S2InitCommand, build: suspend () -> Pair<ENTITY, EVENT>): EVENT

	suspend fun <T> doTransition(command: S2Command<ID>, exec: suspend ENTITY.() -> Pair<ENTITY, T>): T
	suspend fun <T> doTransition(id: ID, command: S2Command<ID>, exec: suspend ENTITY.() -> Pair<ENTITY, T>): T
}
