package s2.sourcing.dsl

import f2.dsl.fnc.F2Function
import s2.dsl.automate.Cmd
import s2.dsl.automate.Evt
import s2.dsl.automate.Msg

actual fun interface Decide<COMMAND, EVENT> : F2Function<COMMAND, EVENT>
		where COMMAND : Cmd,
			  EVENT : Evt
