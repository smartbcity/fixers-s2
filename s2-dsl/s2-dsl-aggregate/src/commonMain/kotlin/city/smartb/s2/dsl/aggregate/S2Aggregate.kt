package city.smartb.s2.dsl.aggregate

import city.smartb.s2.dsl.aggregate.entity.WithS2State
import city.smartb.s2.dsl.automate.S2Command
import city.smartb.s2.dsl.automate.S2InitCommand
import city.smartb.s2.dsl.automate.S2State

interface S2Aggregate<STATE: S2State, ID, ENTITY: WithS2State<STATE>> {

	suspend fun <EVENT> createWithEvent(command: S2InitCommand, buildEvent: suspend ENTITY.() -> EVENT, buildEntity: suspend () -> ENTITY): EVENT
	suspend fun <EVENT> createWithEvent(command: S2InitCommand, build: suspend () -> Pair<ENTITY, EVENT>): EVENT

	suspend fun <T> doTransition(command: S2Command<ID>, save: Boolean = true, exec: suspend ENTITY.() -> T): T
	suspend fun <T> doTransition(id: ID, command: S2Command<ID>, save: Boolean = true, exec: suspend ENTITY.() -> T): T

}