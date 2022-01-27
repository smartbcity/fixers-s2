package s2.dsl.automate.builder

import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2InitTransition
import s2.dsl.automate.S2State
import s2.dsl.automate.S2SubMachine
import s2.dsl.automate.S2Transition
import kotlin.js.JsExport
import kotlin.js.JsName

class S2AutomateBuilder<STATE: S2State, ID> {
	lateinit var name: String
	lateinit var init: S2InitTransition
	val transactions = mutableListOf<S2Transition>()
	val subMachines = mutableListOf<S2SubMachine>()

	inline fun <reified CMD: S2InitCommand> init(exec: S2InitTransitionBuilder<STATE>.() -> Unit) {
		val builder = S2InitTransitionBuilder<STATE>()
		builder.exec()
		init = S2InitTransition(
			to = builder.to,
			role = builder.role,
			command = CMD::class,
		)
	}

	inline fun <reified CMD: S2Command<ID>> transaction(exec: S2TransitionBuilder.() -> Unit) {
		val builder = S2TransitionBuilder()
		builder.exec()
		S2Transition(
			from = builder.from,
			to = builder.to,
			role = builder.role,
			command = CMD::class,
		).let(transactions::add)
	}

	inline fun <reified CMD: S2Command<ID>> selfTransaction(exec: S2SelfTransitionBuilder.() -> Unit) {
		val builder = S2SelfTransitionBuilder()
		builder.exec()
		builder.states.map { state ->
			S2Transition(
				from = state,
				to = state,
				role = builder.role,
				command = CMD::class,
			)
		}.forEach(transactions::add)
	}

	fun node(exec: S2NodeBuilder<ID>.() -> Unit) {
		val builder = S2NodeBuilder<ID>()
		builder.exec()
		transactions.addAll(builder.transactions)
	}

	fun submachine(exec: S2SubMachineBuilder<ID>.() -> Unit) {
		val builder = S2SubMachineBuilder<ID>()
		builder.exec()
		S2SubMachine(
			automate = builder.automate,
			startsOn = builder.startsOn,
			endsOn = builder.endsOn,
			autostart = builder.autostart,
			blocking = builder.blocking,
			singleton = builder.singleton
		).let(subMachines::add)
	}
}

@JsExport
@JsName("s2")
fun <ID, STATE: S2State> s2(exec: S2AutomateBuilder<STATE, ID>.() -> Unit): S2Automate {
	val builder = S2AutomateBuilder<STATE, ID>()
	builder.exec()
	return S2Automate(
		name = builder.name,
		init = builder.init,
		transitions = builder.transactions.toTypedArray(),
		subMachines = builder.subMachines.toTypedArray()
	)
}
