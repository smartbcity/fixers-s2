package s2.automate.sourcing.automate

import s2.dsl.automate.Automate
import s2.dsl.automate.Msg
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.S2Transition
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("S2SourcingAutomate")
class S2SourcingAutomate(
	val name: String,
	val transitions: Array<S2Transition>,
	val subMachines: Array<S2StormingSubMachine>
): Automate {
	override fun getAvailableTransitions(state: S2State): List<S2Transition> {
		return transitions.filter { isSameState(it.from, state) }
	}

	override fun isAvailableTransition(currentState: S2State, command: Msg): Boolean {
		return getAvailableTransitions(currentState).any { it.command.isInstance(command) }
	}

	override fun isAvailableInitTransition(command: S2InitCommand): Boolean {
		return transitions.any { it.from == null && it.command.isInstance(command) }
	}

	override fun isFinalState(state: S2State): Boolean {
		return getAvailableTransitions(state).isEmpty()
	}

	override fun isSameState(from: S2State?, to: S2State): Boolean {
		return from?.position == to.position
	}
}
