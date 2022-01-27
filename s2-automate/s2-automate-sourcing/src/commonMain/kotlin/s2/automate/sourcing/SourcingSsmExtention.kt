package s2.automate.sourcing

import s2.automate.sourcing.automate.S2SourcingAutomate
import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2Transition
import s2.dsl.automate.ssm.toSsmTransitions
import ssm.chaincode.dsl.model.Ssm
import ssm.chaincode.dsl.model.SsmTransition

fun S2SourcingAutomate.toSsm() = Ssm(
	name = this.name,
	transitions = this.transitions.toSsmTransitions()
)
