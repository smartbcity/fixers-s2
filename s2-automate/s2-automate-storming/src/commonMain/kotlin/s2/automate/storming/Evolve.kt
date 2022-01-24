package s2.automate.storming

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

interface Evolve<ENTITY, EVENT : Event> : F2Function<Pair<EVENT, ENTITY?>, ENTITY?>
