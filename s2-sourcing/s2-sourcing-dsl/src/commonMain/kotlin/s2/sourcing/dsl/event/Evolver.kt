package s2.sourcing.dsl.event

interface Evolver<ENTITY, EVENT> {

	suspend fun evolve(model: ENTITY?, event: EVENT): ENTITY?
}
