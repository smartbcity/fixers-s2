package s2.sourcing.dsl.event

import f2.dsl.cqrs.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold

open class SourcingProjectionBuilder<ENTITY, EVENT, ID>(
	private val eventStore: EventPersister<EVENT, ID>,
	private val evolver: Evolver<ENTITY, EVENT>
) where
EVENT : Event {

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
