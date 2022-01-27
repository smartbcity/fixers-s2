package s2.automate.sourcing.builder

import f2.dsl.cqrs.Event
import s2.dsl.automate.builder.S2SelfTransitionBuilder
import s2.dsl.automate.builder.S2TransitionBuilder
import s2.automate.sourcing.automate.S2StormingAutomate
import s2.automate.sourcing.automate.S2StormingSubMachine
import s2.automate.sourcing.automate.S2StormingTransition
import kotlin.js.JsExport
import kotlin.js.JsName

class S2StormingAutomateBuilder<ID> {
	lateinit var name: String
	val transactions = mutableListOf<S2StormingTransition>()
	val subMachines = mutableListOf<S2StormingSubMachine<ID>>()

//	inline fun <reified MSG: WithId<ID>> init(exec: S2TransitionBuilder.() -> Unit) {
//		val builder = S2TransitionBuilder()
//		builder.exec()
//		S2Transition(
//			from = null,
//			to = builder.to,
//			role = builder.role,
//			command = MSG::class,
//		).let(transactions::add)
//	}

	inline fun <reified MSG: Event> transaction(exec: S2TransitionBuilder.() -> Unit) {
		val builder = S2TransitionBuilder()
		builder.exec()
		S2StormingTransition(
			from = builder.from,
			to = builder.to,
			role = builder.role,
			msg = MSG::class,
		).let(transactions::add)
	}

	inline fun <reified MSG: Event> selfTransaction(exec: S2SelfTransitionBuilder.() -> Unit) {
		val builder = S2SelfTransitionBuilder()
		builder.exec()
		builder.states.map { state ->
			S2StormingTransition(
				from = state,
				to = state,
				role = builder.role,
				msg = MSG::class,
			)
		}.forEach(transactions::add)
	}

	fun submachine(exec: S2StormingSubMachineBuilder<ID>.() -> Unit) {
		val builder = S2StormingSubMachineBuilder<ID>()
		builder.exec()
		S2StormingSubMachine(
			automate = builder.automate,
		).let(subMachines::add)
	}
}

@JsExport
@JsName("s2")
fun <ID> s2Storming(exec: S2StormingAutomateBuilder<ID>.() -> Unit): S2StormingAutomate<ID> {
	val builder = S2StormingAutomateBuilder<ID>()
	builder.exec()
	return S2StormingAutomate(
		name = builder.name,
		transitions = builder.transactions.toTypedArray(),
		subMachines = builder.subMachines.toTypedArray(),
	)
}
