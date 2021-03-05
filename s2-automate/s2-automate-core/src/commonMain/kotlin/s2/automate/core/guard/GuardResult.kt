package s2.automate.core.guard

import s2.dsl.automate.S2Error

interface GuardResult {
	companion object {
		fun valid(): GuardResult = GuardResultBase(emptyList())
		fun error(vararg erros: S2Error): GuardResult = GuardResultBase(erros.asList())
		fun error(erros: List<S2Error>): GuardResult = GuardResultBase(erros)
	}
	val errors: List<S2Error>
	fun isValid(): Boolean
}

class GuardResultBase(
	override val errors: List<S2Error>
) : GuardResult {
	override fun isValid(): Boolean = errors.isEmpty()
}
