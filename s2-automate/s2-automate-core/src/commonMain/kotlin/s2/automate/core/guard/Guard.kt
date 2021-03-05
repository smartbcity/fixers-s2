package s2.automate.core.guard

import s2.automate.core.context.InitTransitionContext
import s2.automate.core.context.TransitionContext
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.dsl.automate.S2State

interface Guard<STATE, ID, ENTITY> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {

	/**
	 * Evaluate a guard condition for init transition.
	 *
	 * @param context the state context init
	 * @return true, if guard evaluation is successful, false otherwise.
	 */
	fun evaluateInit(context: InitTransitionContext<STATE, ID, ENTITY>): GuardResult

	/**
	 * Evaluate a guard condition for transition.
	 *
	 * @param context the state context transaction
	 * @return true, if guard evaluation is successful, false otherwise.
	 */
	fun evaluateTransition(context: TransitionContext<STATE, ID, ENTITY>): GuardResult
}