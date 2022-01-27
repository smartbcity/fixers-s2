package s2.automate.core.guard

import f2.dsl.cqrs.Command
import s2.automate.core.GuardExecutor
import s2.automate.core.appevent.AutomateTransitionNotAccepted
import s2.automate.core.appevent.publisher.AutomateEventPublisher
import s2.automate.core.context.InitTransitionAppliedContext
import s2.automate.core.context.InitTransitionContext
import s2.automate.core.context.TransitionAppliedContext
import s2.automate.core.context.TransitionContext
import s2.automate.core.error.AutomateException
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

class GuardExecutorImpl<STATE, ID, ENTITY, AUTOMATE>(
	private val guards: List<Guard<STATE, ID, ENTITY, AUTOMATE>>,
	private val publisher: AutomateEventPublisher<STATE, ID, ENTITY, AUTOMATE>,
): GuardExecutor<STATE, ID, ENTITY, AUTOMATE> where
	STATE : S2State,
	ENTITY : WithS2State<STATE>,
	ENTITY : WithS2Id<ID> {

	override suspend fun evaluateInit(context: InitTransitionContext<AUTOMATE>) {
		val result = guards.map { it.evaluateInit(context) }.flatten()
		handleResult(result, context.command)
	}

	override suspend fun evaluateTransition(context: TransitionContext<STATE, ID, ENTITY, AUTOMATE>) {
		val result = guards.map { it.evaluateTransition(context) }.flatten()
		handleResult(result, context.command, context.from)
	}

	private fun List<GuardResult>.flatten(): GuardResult {
		val errors = flatMap { it.errors }
		return GuardResult.error(errors.toList())
	}

	override suspend fun verifyInitTransition(context: InitTransitionAppliedContext<STATE, ID, ENTITY, AUTOMATE>) {
		val result = guards.map { it.verifyInitTransition(context) }.flatten()
		handleResult(result, context.command)
	}

	override suspend fun verifyTransition(context: TransitionAppliedContext<STATE, ID, ENTITY, AUTOMATE>) {
		val result = guards.map { it.verifyTransition(context) }.flatten()
		handleResult(result, context.command, context.from)
	}

	private fun handleResult(
		result: GuardResult,
		command: Command,
		from: S2State? = null,
	) {
		if (result.isValid().not()) {
			publisher.automateTransitionNotAccepted(
				AutomateTransitionNotAccepted(
					from = from,
					command = command
				)
			)
			throw AutomateException(result.errors)
		}
	}
}
