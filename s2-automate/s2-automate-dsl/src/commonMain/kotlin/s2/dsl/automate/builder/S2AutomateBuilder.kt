package s2.dsl.automate.builder

import f2.dsl.cqrs.Message
import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2SubMachine
import s2.dsl.automate.S2Transition
import kotlin.js.JsExport
import kotlin.js.JsName
import s2.dsl.automate.Cmd

class S2AutomateBuilder {
	lateinit var name: String
	val transactions = mutableListOf<S2Transition>()
	val subMachines = mutableListOf<S2SubMachine>()

	inline fun <reified CMD: S2InitCommand> init(exec: S2InitTransitionBuilder.() -> Unit) {
		val builder = S2InitTransitionBuilder()
		builder.exec()
		S2Transition(
			to = builder.to,
			role = builder.role,
			msg = CMD::class,
			from = null
		).let(transactions::add)
	}

	inline fun <reified CMD: Cmd> transaction(exec: S2TransitionBuilder.() -> Unit) {
		val builder = S2TransitionBuilder()
		builder.exec()
		S2Transition(
			from = builder.from,
			to = builder.to,
			role = builder.role,
			msg = CMD::class,
		).let(transactions::add)
	}

	inline fun <reified CMD: Cmd> selfTransaction(exec: S2SelfTransitionBuilder.() -> Unit) {
		val builder = S2SelfTransitionBuilder()
		builder.exec()
		builder.states.map { state ->
			S2Transition(
				from = state,
				to = state,
				role = builder.role,
				msg = CMD::class,
			)
		}.forEach(transactions::add)
	}

	fun node(exec: S2NodeBuilder.() -> Unit) {
		val builder = S2NodeBuilder()
		builder.exec()
		transactions.addAll(builder.transactions)
	}

	fun submachine(exec: S2SubMachineBuilder.() -> Unit) {
		val builder = S2SubMachineBuilder()
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
fun s2(exec: S2AutomateBuilder.() -> Unit): S2Automate {
	val builder = S2AutomateBuilder()
	builder.exec()
	return S2Automate(
		name = builder.name,
		transitions = builder.transactions.toTypedArray(),
		subMachines = builder.subMachines.toTypedArray()
	)
}
