package s2.automate.storming.event

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold
import s2.dsl.automate.S2State

open class StormingProjectionBuilder<ENTITY, EVENT, STATE, ID>(
	private val eventStore: EventPersister<EVENT, ID>,
	private val evolver: Evolver<ENTITY, EVENT>
) where STATE : S2State {

	suspend fun replay(id: ID): ENTITY? {
		return eventStore.load(id).let { events ->
			replay(events)
		}
	}

	suspend fun replayAndEvolve(id: ID, news: Flow<EVENT>): ENTITY? {
		return eventStore.load(id).let { events ->
			replay(events)
		}.let { entity ->
			replay(entity, news)
		}
	}

	suspend fun replay(events: Flow<EVENT>): ENTITY? {
		return replay(null, events)
	}

	private suspend fun replay(entity: ENTITY?, events: Flow<EVENT>): ENTITY? {
		return events.fold(entity) { updated, event ->
			evolver.evolve(updated, event)
		}
	}
}
