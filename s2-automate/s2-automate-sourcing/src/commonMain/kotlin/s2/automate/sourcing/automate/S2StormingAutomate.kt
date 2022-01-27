package s2.automate.sourcing.automate

import s2.dsl.automate.Automate
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.S2Transition
import ssm.chaincode.dsl.model.Ssm
import ssm.chaincode.dsl.model.SsmTransition
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("S2StormingAutomate")
class S2StormingAutomate<ID>(
	val name: String,
	val transitions: Array<S2Transition<ID>>,
	val subMachines: Array<S2StormingSubMachine<ID>>
): Automate<ID> {
	override fun getAvailableTransitions(state: S2State): List<S2Transition<ID>> {
		return transitions.filter { isSameState(it.from, state) }
	}

	override fun isAvailableTransition(currentState: S2State, command: S2Command<ID>): Boolean {
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

fun S2StormingAutomate<*>.toSsm() = Ssm(
	name = this.name,
	transitions = this.transitions.toSsmTransitions()
)


fun Array<out S2Transition<*>>.toSsmTransitions() =filter { it.from != null }. map { it.toSsmTransition() }

fun S2Transition<*>.toSsmTransition() = SsmTransition(
	from = this.from!!.position,
	to = this.to.position,
	role = this.role::class.simpleName!!,
	action = this.command.simpleName!!
)

