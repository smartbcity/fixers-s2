package s2.dsl.automate.ssm

import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2Transition
import ssm.chaincode.dsl.model.Ssm
import ssm.chaincode.dsl.model.SsmTransition

fun S2Automate<*>.toSsm() = Ssm(
	name = this.name,
	transitions = this.transitions.toSsmTransitions()
)

fun Array<out S2Transition<*>>.toSsmTransitions() = map { it.toSsmTransition() }

fun S2Transition<*>.toSsmTransition() = SsmTransition(
	from = this.from.position,
	to = this.to.position,
	role = this.role::class.simpleName!!,
	action = this.command.simpleName!!
)
