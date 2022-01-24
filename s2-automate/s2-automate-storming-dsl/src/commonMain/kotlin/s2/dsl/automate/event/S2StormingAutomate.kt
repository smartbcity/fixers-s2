package s2.dsl.automate.event

import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.S2Transition
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("S2StormingAutomate")
class S2StormingAutomate<ID>(
	val name: String,
	val transitions: Array<S2Transition<ID>>,
	val subMachines: Array<S2StormingSubMachine<ID>>
) {
	fun getAvailableTransitions(state: S2State): List<S2Transition<ID>> {
		return transitions.filter { isSameState(it.from, state) }
	}

	fun isAvailableTransition(currentState: S2State, command: S2Command<*>): Boolean {
		return getAvailableTransitions(currentState).any { it.command.isInstance(command) }
	}

	fun isAvailableInitTransition(command: S2InitCommand): Boolean {
		return transitions.any { it.from == null && it.command.isInstance(command) }
	}

	fun isFinalState(state: S2State): Boolean {
		return getAvailableTransitions(state).isEmpty()
	}

	fun isSameState(from: S2State?, to: S2State): Boolean {
		return from?.position == to.position
	}
}
