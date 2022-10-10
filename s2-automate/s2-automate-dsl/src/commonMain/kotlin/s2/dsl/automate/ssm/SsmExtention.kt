package s2.dsl.automate.ssm

import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2Transition
import ssm.chaincode.dsl.model.Ssm
import ssm.chaincode.dsl.model.SsmTransition

fun S2Automate.toSsm(permissive: Boolean = false) = Ssm(
	name = this.name,
	transitions = if(permissive) {
		this.transitions.toSsmTransitions(0, 1)
	} else {
		this.transitions.toSsmTransitions()
	}
)

fun Array<out S2Transition>.toSsmTransitions(
	from: Int? = null, to: Int? = null
) =filter { it.from != null }. map { it.toSsmTransition(from, to) }

fun S2Transition.toSsmTransition(from: Int? = null, to: Int? = null) = SsmTransition(
	from = from ?: this.from!!.position,
	to = to ?: this.to.position,
	role = this.role::class.simpleName!!,
	action = this.evt?.simpleName ?: this.cmd.simpleName!!
)
