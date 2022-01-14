package s2.dsl.automate.builder

import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2InitTransition
import s2.dsl.automate.S2State
import s2.dsl.automate.S2Transition
import kotlin.js.JsExport
import kotlin.js.JsName

class S2AutomateBuilder2<STATE : S2State, ID> {
	lateinit var name: String
	lateinit var init: S2InitTransition
	var transactions: MutableList<S2Transition> = mutableListOf()

	inline fun <reified CMD : S2InitCommand> init(exec: S2InitTransitionBuilder<CMD, STATE>.() -> Unit) {
		val builder = S2InitTransitionBuilder<CMD, STATE>()
		builder.exec()
		init = S2InitTransition(
			to = builder.to,
			role = builder.role,
			command = CMD::class,
		)
	}

	fun step(exec: S2StepBuilder<ID>.() -> Unit) {
		val builder = S2StepBuilder<ID>()
		builder.exec()
		// S2Step
	}
}

@JsExport
@JsName("s22")
fun <ID, STATE : S2State> s22(exec: S2AutomateBuilder2<STATE, ID>.() -> Unit): S2Automate {
	val builder = S2AutomateBuilder2<STATE, ID>()
	builder.exec()
	return S2Automate(
		name = builder.name,
		init = builder.init,
		transitions = builder.transactions.toTypedArray()
	)
}

@JsExport
@JsName("S2TransitionBuilder")
class S2StepBuilder<ID> {
	lateinit var state: S2State
	val transactions: MutableList<S2Transition> = mutableListOf()

	inline fun <reified CMD : S2Command<ID>> transaction(
		exec: S2TransitionBuilder<ID, CMD>.() -> Unit,
	) {
		val builder = S2TransitionBuilder<ID, CMD>()
		builder.exec()
		val transition = S2Transition(
			from = builder.from,
			to = builder.to,
			role = builder.role,
			command = CMD::class.simpleName!!,
		)
		transactions.add(transition)
	}
}
