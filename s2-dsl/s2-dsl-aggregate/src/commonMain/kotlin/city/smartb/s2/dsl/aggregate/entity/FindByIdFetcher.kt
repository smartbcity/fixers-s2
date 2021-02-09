package city.smartb.s2.dsl.aggregate.entity

interface FindByIdFetcher<ENTITY, ID> {

	suspend fun findById(id: ID): ENTITY?
}