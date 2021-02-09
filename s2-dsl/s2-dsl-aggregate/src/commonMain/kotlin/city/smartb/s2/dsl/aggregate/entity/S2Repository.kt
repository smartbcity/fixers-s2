package city.smartb.s2.dsl.aggregate.entity

interface S2Repository<ENTITY, ID>: FindByIdFetcher<ENTITY, ID>
{

	suspend fun save(entity: ENTITY): ENTITY
	override suspend fun findById(id: ID): ENTITY?
}