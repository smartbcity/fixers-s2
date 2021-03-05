package s2.dsl.automate

import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("S2Automate")
class S2Automate(
	val name: String,
	val init: S2InitTransition,
	val transitions: Array<S2Transition>
) {
	fun getAvailableTransition(state: S2State): List<S2Transition> {
		return transitions.filter {
			it.from.position == state.position
		}
	}

	fun isAvailableTransition(currentState: S2State, command: S2Command<*>): Boolean {
		return getAvailableTransition(currentState).map { it.command }.contains(command::class.simpleName)
	}

	fun isAvailableInitTransition(currentState: S2State, command: S2InitCommand): Boolean {

		return init.command == command::class
	}

	fun isFinalState(state: S2State): Boolean {
		return getAvailableTransition(state).isEmpty()
	}

	fun isSameState(from: S2State, to: S2State): Boolean {
		return from.position == to.position
	}

}

@JsExport
@JsName("S2State")
interface S2State {
	val position: Int

	@JsName("nodePosition")
	fun nodePosition() = position
}

@JsExport
@JsName("S2Role")
interface S2Role
