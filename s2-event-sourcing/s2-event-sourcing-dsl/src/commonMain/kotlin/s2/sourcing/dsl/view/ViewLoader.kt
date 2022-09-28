package s2.sourcing.dsl.view

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import s2.dsl.automate.Evt
import s2.dsl.automate.model.WithS2Id
import s2.sourcing.dsl.Loader
import s2.sourcing.dsl.event.EventRepository

open class ViewLoader<EVENT, ENTITY, ID>(
	private val eventRepository: EventRepository<EVENT, ID>,
	private val view: View<EVENT, ENTITY>
): Loader<EVENT, ENTITY, ID> where
EVENT: Evt,
EVENT: WithS2Id<ID> {

	override suspend fun load(id: ID): ENTITY? {
		return eventRepository.load(id).let { events ->
			load(events)
		}
	}

	override suspend fun load(events: Flow<EVENT>): ENTITY? {
		return evolve(events, null)
	}

	override suspend fun loadAndEvolve(id: ID, news: Flow<EVENT>): ENTITY? {
		return eventRepository.load(id).let { events ->
			load(events)
		}.let { entity ->
			evolve(news, entity)
		}
	}

	override suspend fun evolve(events: Flow<EVENT>, entity: ENTITY?): ENTITY? {
		return events.fold(entity) { updated, event ->
			view.evolve(event, updated)
		}
	}

	override suspend fun reloadHistory(): List<ENTITY> = eventRepository.loadAll()
		.groupBy { event -> event.s2Id() }
		.reducePerKey(::load)
		.mapNotNull { (_, entity) -> entity }
		.toList()

	// https://stackoverflow.com/a/58678049
	private fun <T, K> Flow<T>.groupBy(getKey: (T) -> K): Flow<Pair<K, Flow<T>>> = flow {
		val storage = mutableMapOf<K, SendChannel<T>>()
		try {
			collect { t ->
				val key = getKey(t)
				storage.getOrPut(key) {
					Channel<T>(32).also { emit(key to it.consumeAsFlow()) }
				}.send(t)
			}
		} finally {
			storage.values.forEach { chan -> chan.close() }
		}
	}

	@OptIn(FlowPreview::class)
	private fun <T, K, R> Flow<Pair<K, Flow<T>>>.reducePerKey(reduce: suspend (Flow<T>) -> R): Flow<Pair<K, R>> {
		return flatMapMerge { (key, flow) ->
			flow { emit(key to reduce(flow)) }
		}
	}
}
