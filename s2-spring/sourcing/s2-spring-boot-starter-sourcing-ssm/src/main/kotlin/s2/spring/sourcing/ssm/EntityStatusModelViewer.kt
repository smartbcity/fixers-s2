package s2.spring.sourcing.ssm

import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.sourcing.dsl.event.Evolver

class StateEntity<STATE, ID>(
	val id: ID,
	var state: STATE
) : WithS2State<STATE>, WithS2Id<ID> {
	override fun s2State() = state
	override fun s2Id() = id
}

class EntityStatusModelViewer<STATE, ID, EVENT> : Evolver<StateEntity<STATE, ID>, EVENT> where
EVENT : WithS2Id<ID>,
EVENT : WithS2State<STATE> {
	override suspend fun evolve(model: StateEntity<STATE, ID>?, event: EVENT): StateEntity<STATE, ID> {
		return model?.apply {
			state = event.s2State()
		} ?: StateEntity(id = event.s2Id(), state = event.s2State())
	}
}
