package s2.automate.core.context

import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.dsl.automate.S2State

class InitTransitionContext<STATE, ID, ENTITY>(
	val automateContext: AutomateContext<STATE, ID, ENTITY>,
	val command: S2InitCommand,
	val to: STATE,
) where
	STATE : S2State,
	ENTITY : WithS2State<STATE>,
	ENTITY : WithS2Id<ID>