package s2.spring.sourcing.ssm.noview//package s2.spring.sourcing.ssm
//
//import s2.dsl.automate.Evt
//import s2.dsl.automate.Msg
//import s2.dsl.automate.model.WithS2Id
//import s2.dsl.automate.model.WithS2State
//import s2.sourcing.dsl.view.View
//
//class StateEntity<STATE, ID>(
//	val id: ID,
//	var state: STATE
//) : WithS2State<STATE>, WithS2Id<ID> {
//	override fun s2State() = state
//	override fun s2Id() = id
//}
//
//class EntityStatusModelViewer<STATE, ID, EVENT> : View<EVENT, StateEntity<STATE, ID>> where
//EVENT : WithS2Id<ID>,
//EVENT : WithS2State<STATE>,
//EVENT : Evt {
//	override suspend fun evolve(event: EVENT, model: StateEntity<STATE, ID>?): StateEntity<STATE, ID> {
//		return model?.apply {
//			state = event.s2State()
//		} ?: StateEntity(id = event.s2Id(), state = event.s2State())
//	}
//}
