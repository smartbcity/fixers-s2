package city.smartb.s2.dsl.automate.builder

import city.smartb.s2.dsl.automate.*
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KClass

class S2AutomateBuilder<ID, STATE : S2State> {
	lateinit var name: String
	lateinit var init: S2InitTransition
	var transactions: MutableList<S2Transition> = mutableListOf()

	fun <CMD : S2InitCommand> init(to: S2State? = null, exec: S2InitTransitionBuilder<CMD, STATE>.() -> Unit) {
		val builder = S2InitTransitionBuilder<CMD, STATE>()
		builder.exec()
		init = S2InitTransition(
			to = to ?: builder.to,
			role = builder.role,
			command = builder.cmd,
		)
	}

	fun <CMD : S2Command<ID>> transaction(
		form: S2State? = null,
		to: S2State? = null,
		exec: S2TransitionBuilder<ID, CMD>.() -> Unit
	) {
		val builder = S2TransitionBuilder<ID, CMD>()
		builder.exec()
		val transition = S2Transition(
			from = form ?: builder.form,
			to = to ?: builder.to,
			role = builder.role,
			command = builder.cmd.simpleName!!,
		)
		transactions.add(transition)
	}
}

@JsExport
@JsName("s2")
fun <ID, STATE : S2State> s2(exec: S2AutomateBuilder<ID, STATE>.() -> Unit): S2Automate {
	val builder = S2AutomateBuilder<ID, STATE>()
	builder.exec()
	return S2Automate(
		name = builder.name,
		init = builder.init,
		transitions = builder.transactions.toTypedArray()
	)
}

@JsExport
@JsName("S2TransitionBuilder")
class S2TransitionBuilder<ID, CMD : S2Command<ID>> {
	lateinit var form: S2State
	lateinit var to: S2State
	lateinit var role: S2Role
	lateinit var cmd: KClass<out CMD>
}

class S2InitTransitionBuilder<CMD : S2InitCommand, STATE : S2State> {
	lateinit var to: STATE
	lateinit var role: S2Role
	lateinit var cmd: KClass<out CMD>
}
