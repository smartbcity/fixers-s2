package city.smartb.s2.dsl.aggregate.event

import city.smartb.f2.dsl.cqrs.Command
import city.smartb.s2.dsl.aggregate.entity.WithS2Id
import city.smartb.s2.dsl.aggregate.entity.WithS2State
import city.smartb.s2.dsl.automate.S2Command
import city.smartb.s2.dsl.automate.S2InitCommand
import city.smartb.s2.dsl.automate.S2State

sealed class AggregateAppEvent

class CommandSnapAppEvent<COMMAND: Command>(
	val command: COMMAND
): AggregateAppEvent()

class StateSnapAppEvent<STATE, ID, ENTITY>(
	val entity: ENTITY
): AggregateAppEvent()
		where STATE : S2State, ENTITY : WithS2State<STATE>, ENTITY : WithS2Id<ID>

class EntitySnapshotAppEvent<STATE, ID, ENTITY>(
	val entity: ENTITY
): AggregateAppEvent()
		where STATE: S2State, ENTITY : WithS2State<STATE>, ENTITY: WithS2Id<ID>

