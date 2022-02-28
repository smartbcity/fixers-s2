package s2.automate.core.guard

import s2.automate.core.context.InitTransitionAppliedContext
import s2.automate.core.context.InitTransitionContext
import s2.automate.core.context.TransitionAppliedContext
import s2.automate.core.context.TransitionContext
import s2.dsl.automate.Automate
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

open class GuardAdapter<STATE, ID, ENTITY, AUTOMATE> : Guard<STATE, ID, ENTITY, AUTOMATE> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
AUTOMATE : Automate
{

	override suspend fun evaluateInit(context: InitTransitionContext<AUTOMATE>) = GuardResult.valid()

	override suspend fun evaluateTransition(context: TransitionContext<STATE, ID, ENTITY, AUTOMATE>) = GuardResult.valid()

	override suspend fun verifyInitTransition(context: InitTransitionAppliedContext<STATE, ID, ENTITY, AUTOMATE>) = GuardResult.valid()
	override suspend fun verifyTransition(context: TransitionAppliedContext<STATE, ID, ENTITY, AUTOMATE>) = GuardResult.valid()
}
