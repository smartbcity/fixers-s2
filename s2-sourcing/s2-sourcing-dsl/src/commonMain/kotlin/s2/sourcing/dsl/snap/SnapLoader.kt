package s2.sourcing.dsl.snap

import kotlinx.coroutines.flow.Flow
import s2.dsl.automate.Evt
import s2.sourcing.dsl.Loader
import s2.sourcing.dsl.view.ViewLoader

class SnapLoader<EVENT, ENTITY, ID>(
	private val snapRepository: SnapRepository<ENTITY, ID>,
	private val viewLoader: ViewLoader<EVENT, ENTITY, ID>
) : Loader<EVENT, ENTITY, ID> where
EVENT : Evt {

	override suspend fun load(id: ID): ENTITY? {
		return snapRepository.get(id) ?: viewLoader.load(id)
	}

	override suspend fun loadAndEvolve(id: ID, news: Flow<EVENT>): ENTITY? {
		return load(id).let { entity ->
			viewLoader.evolve(news, entity)
		}?.let { entity ->
			snapRepository.save(entity)
		}
	}

	override suspend fun load(events: Flow<EVENT>): ENTITY? {
		return viewLoader.load(events)
	}

	override suspend fun evolve(events: Flow<EVENT>, entity: ENTITY?): ENTITY? {
		return viewLoader.evolve(events,entity)
	}
}
