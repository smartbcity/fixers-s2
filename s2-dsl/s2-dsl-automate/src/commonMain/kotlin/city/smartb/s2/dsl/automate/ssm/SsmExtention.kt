package city.smartb.s2.dsl.automate.ssm

import city.smartb.s2.dsl.automate.S2Automate
import city.smartb.s2.dsl.automate.S2Transition

fun S2Automate.toSsm() = Ssm(
		name = this.name,
		transitions = this.transitions.toSsmTransition()
)

fun Array<S2Transition>.toSsmTransition() = map { it.toSsmTransition() }

fun S2Transition.toSsmTransition() = SsmTransition(
		from = this.from.position,
		to = this.to.position,
		role = this.role::class.simpleName!!,
		command = this.command::class.simpleName!!
)