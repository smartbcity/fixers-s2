package city.smartb.s2.dsl.aggregate.exception

import city.smartb.s2.dsl.automate.S2State
import city.smartb.f2.dsl.cqrs.Command

class InvalidTransitionException(
	val state: S2State?,
	val command: Command,
) : Exception(
		"Not available transition from ${state} with command ${command}")