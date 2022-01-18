package s2.automate.core.context

import s2.automate.core.guard.Guard
import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

class AutomateContext<STATE, ID, ENTITY>(
	val automate: S2Automate<ID>,
	val guards: List<Guard<STATE, ID, ENTITY>>,
) where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>
