package s2.sourcing.dsl

import f2.dsl.fnc.F2Function
import s2.dsl.automate.Evt

interface Evolve<ENTITY, EVENT : Evt> : F2Function<Pair<EVENT, ENTITY?>, ENTITY?>
