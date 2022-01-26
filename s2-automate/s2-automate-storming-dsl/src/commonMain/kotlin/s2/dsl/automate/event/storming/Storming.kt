package s2.dsl.automate.event.storming

import f2.dsl.cqrs.Command
import f2.dsl.fnc.F2Function
import s2.dsl.automate.S2Event
import s2.dsl.automate.S2State
import s2.dsl.automate.event.storming.Decide

interface Storming<ID, STATE : S2State, ENTITY, COMMAND : Command, EVENT : S2Event<STATE, ID>> : F2Function<COMMAND, EVENT> {
	val decide: Decide<ID, STATE, COMMAND, EVENT>
	val evolve: Evolve<ENTITY, EVENT>
}
