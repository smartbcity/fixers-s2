package s2.sourcing.dsl.view

interface ViewRepository<ENTITY, ID> {
	suspend fun load(id: ID): ENTITY?
	suspend fun persist(event: ENTITY): ENTITY
}
