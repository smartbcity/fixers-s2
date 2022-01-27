package s2.automate.core.context

import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

class InitTransitionContext<AUTOMATE>(
	val automateContext: AutomateContext<AUTOMATE>,
	val command: S2InitCommand,
)
