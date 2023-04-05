package s2.bdd.repository

interface AssertionEntity<ENTITY, ID: Any, ASSERTER> {
    suspend fun exists(id: ID)
    suspend fun notExists(id: ID)
    suspend fun assertThatId(id: ID): ASSERTER
    suspend fun assertThat(entity: ENTITY): ASSERTER
}
