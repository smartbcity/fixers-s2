package s2.sourcing.dsl.view

interface View<ENTITY, EVENT> {
	suspend fun evolve(model: ENTITY?, event: EVENT): ENTITY?
}
