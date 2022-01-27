package s2.sourcing.dsl

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

actual fun interface Decide<COMMAND, EVENT> : F2Function<COMMAND, EVENT>
		where COMMAND : Command,
			  EVENT : Event
