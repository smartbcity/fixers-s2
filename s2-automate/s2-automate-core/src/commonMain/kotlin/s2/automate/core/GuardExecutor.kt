package s2.automate.core

import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.automate.core.context.InitTransitionContext
import s2.automate.core.context.TransitionContext

interface GuardExecutor<STATE, ID, ENTITY> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {
	fun evaluateInit(context: InitTransitionContext<STATE, ID, ENTITY>)
	fun evaluateTransition(context: TransitionContext<STATE, ID, ENTITY>)
}