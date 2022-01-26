package s2.dsl.automate.event.storming

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import s2.dsl.automate.S2State

actual fun interface Decide<ID, STATE : S2State, COMMAND : Command, EVENT> : F2Function<COMMAND, EVENT>
where EVENT : Event
