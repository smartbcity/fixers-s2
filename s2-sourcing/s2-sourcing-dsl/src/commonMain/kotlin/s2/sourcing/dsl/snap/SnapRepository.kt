package s2.sourcing.dsl.snap

interface SnapRepository<ENTITY, ID> {
	suspend fun get(id: ID): ENTITY?
	suspend fun save(entity: ENTITY): ENTITY
}
