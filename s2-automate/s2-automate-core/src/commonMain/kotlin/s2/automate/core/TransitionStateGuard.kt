package s2.automate.core

import s2.automate.core.context.TransitionContext
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.automate.core.error.ERROR_INVALID_TRANSITION
import s2.automate.core.guard.GuardAdapter
import s2.automate.core.guard.GuardResult
import s2.dsl.automate.S2State

class TransitionStateGuard<STATE, ID, ENTITY> : GuardAdapter<STATE, ID, ENTITY>() where
	STATE : S2State,
	ENTITY : WithS2State<STATE>,
	ENTITY : WithS2Id<ID> {

	override fun evaluateTransition(context: TransitionContext<STATE, ID, ENTITY>): GuardResult {
		val state = context.entity.s2State()
		val command = context.command
		val isValid = context.automateContext.automate.isAvailableTransition(state, command)
		return if(isValid) {
			GuardResult.valid()
		} else {
			GuardResult.error(
				ERROR_INVALID_TRANSITION(state.toString(), command.toString())
			)
		}
	}

}