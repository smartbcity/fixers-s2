package s2.automate.core.guard

import s2.automate.core.appevent.AutomateTransitionNotAccepted
import s2.automate.core.appevent.publisher.AutomateAppEventPublisher
import s2.automate.core.context.InitTransitionContext
import s2.automate.core.context.TransitionContext
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.automate.core.GuardExecutor
import s2.automate.core.error.AutomateException
import s2.dsl.automate.S2State

class GuardExecutorImpl<STATE, ID, ENTITY> (
	private val guards: List<Guard<STATE, ID, ENTITY>>,
	private val publisher: AutomateAppEventPublisher<STATE, ID, ENTITY>,
): GuardExecutor<STATE, ID, ENTITY> where
	STATE : S2State,
	ENTITY : WithS2State<STATE>,
	ENTITY : WithS2Id<ID> {

	override fun evaluateInit(context: InitTransitionContext<STATE, ID, ENTITY>) {
		val result = guards.map { it.evaluateInit(context) }.flatten()
		if (result.isValid().not()) {
			publisher.automateTransitionNotAccepted(
				AutomateTransitionNotAccepted(
					to = context.to,
					from = null,
					command = context.command
				)
			)
			throw AutomateException(result.errors)
		}
	}

	override fun evaluateTransition(context: TransitionContext<STATE, ID, ENTITY>) {
		val result = guards.map { it.evaluateTransition(context) }.flatten()
		if (result.isValid().not()) {
			publisher.automateTransitionNotAccepted(
				AutomateTransitionNotAccepted(
					to = context.to,
					from = context.from,
					command = context.command
				)
			)
			throw AutomateException(result.errors)
		}
	}

	private fun List<GuardResult>.flatten(): GuardResult {
		val errors = flatMap { it.errors }
		return GuardResult.error(errors.toList())
	}
}