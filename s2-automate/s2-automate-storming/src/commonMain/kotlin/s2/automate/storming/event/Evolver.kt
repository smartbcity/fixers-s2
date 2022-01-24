package s2.automate.storming.event

import kotlinx.coroutines.flow.Flow

interface Evolver<ENTITY, EVENT> {

	suspend fun evolve(model: ENTITY?, event: EVENT): ENTITY?
}
