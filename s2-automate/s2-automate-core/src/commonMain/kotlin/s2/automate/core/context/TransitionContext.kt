package s2.automate.core.context

import s2.dsl.automate.S2Command
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

class TransitionContext<STATE, ID, ENTITY, AUTOMATE>(
	val automateContext: AutomateContext<STATE, ID, ENTITY, AUTOMATE>,
	val from: STATE,
	val command: S2Command<ID>,
	val entity: ENTITY,
) where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>
