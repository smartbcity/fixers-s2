package s2.dsl.automate

import f2.dsl.cqrs.Event

actual interface S2Event<out STATE : S2State, ID> : Event, WithId<ID>, Msg {
	actual override val id: ID
	actual val type: STATE
}
