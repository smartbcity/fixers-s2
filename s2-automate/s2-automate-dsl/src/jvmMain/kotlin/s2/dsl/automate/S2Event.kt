package s2.dsl.automate

actual interface S2Event<out STATE : S2State, ID> : Evt, WithId<ID> {
	actual override val id: ID
	actual val type: STATE
}
