package city.smartb.s2.dsl.aggregate.extention

import city.smartb.s2.dsl.aggregate.entity.WithS2State
import city.smartb.s2.dsl.aggregate.exception.InvalidTransitionException
import city.smartb.s2.dsl.automate.S2Automate
import city.smartb.s2.dsl.automate.S2Command
import city.smartb.s2.dsl.automate.S2State

fun <ID, ENTITY: WithS2State<out S2State>> S2Command<ID>.checkTransition(entity: ENTITY, s2: S2Automate) {
	val isAvailableTransition = s2.isAvailableTransition(entity.s2State(), this)
	if (!isAvailableTransition) {
		throw InvalidTransitionException(
			state = entity.s2State(),
			command = this,
		)
	}
}