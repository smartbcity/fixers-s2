package s2.automate.storming

import f2.dsl.cqrs.Command
import f2.dsl.fnc.F2Function
import s2.dsl.automate.S2Event
import s2.dsl.automate.S2State

actual fun interface Decide<ID, STATE : S2State, COMMAND : Command, EVENT : S2Event<STATE, ID>> : F2Function<COMMAND, EVENT>
