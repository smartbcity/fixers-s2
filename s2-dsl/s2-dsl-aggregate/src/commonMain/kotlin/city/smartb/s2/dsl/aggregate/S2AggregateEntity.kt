package city.smartb.s2.dsl.aggregate

import city.smartb.s2.dsl.aggregate.entity.WithS2State
import city.smartb.s2.dsl.automate.S2Command
import city.smartb.s2.dsl.automate.S2InitCommand
import city.smartb.s2.dsl.automate.S2State

interface S2AggregateEntity<STATE: S2State, ID, ENTITY: WithS2State<STATE>> {

	suspend fun create(command: S2InitCommand, buildEntity: suspend () -> ENTITY): ENTITY

	suspend fun doTransition(command: S2Command<ID>, exec: suspend ENTITY.() -> ENTITY): ENTITY

}