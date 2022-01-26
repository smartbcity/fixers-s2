package s2.automate.storming.event

interface Evolver<ENTITY, EVENT> {

	suspend fun evolve(model: ENTITY?, event: EVENT): ENTITY?
}
