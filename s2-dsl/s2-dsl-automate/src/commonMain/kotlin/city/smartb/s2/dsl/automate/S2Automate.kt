package city.smartb.s2.dsl.automate

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
