package s2.automate.core.guard

import s2.dsl.automate.S2Error

interface GuardResult {
	companion object {
		fun valid(): GuardResult = GuardResultBase(emptyList())
		fun error(vararg errors: S2Error): GuardResult = GuardResultBase(errors.asList())
		fun error(errors: List<S2Error>): GuardResult = GuardResultBase(errors)
	}
	val errors: List<S2Error>
	fun isValid(): Boolean
}

class GuardResultBase(
	override val errors: List<S2Error>
) : GuardResult {
	override fun isValid(): Boolean = errors.isEmpty()
}
