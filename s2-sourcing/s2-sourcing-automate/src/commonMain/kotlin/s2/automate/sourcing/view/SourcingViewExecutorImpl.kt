package s2.automate.sourcing.view

import s2.dsl.automate.Evt
import s2.sourcing.dsl.view.View
import s2.sourcing.dsl.view.ViewLoader
import s2.sourcing.dsl.snap.SnapRepository

class SourcingViewExecutorImpl< EVENT, ENTITY, ID>(
	private val evolver: View<EVENT, ENTITY>,
	private val viewBuilder: ViewLoader<EVENT, ENTITY, ID>,
	private val viewRepository: SnapRepository<ENTITY, ID>
) : SourcingViewExecutor where
EVENT : Evt {

	suspend fun evolve(id: ID, msg: EVENT){
		val entity = viewRepository.get(id) ?: viewBuilder.load(id)
		evolver.evolve(msg, entity)
	}
}
