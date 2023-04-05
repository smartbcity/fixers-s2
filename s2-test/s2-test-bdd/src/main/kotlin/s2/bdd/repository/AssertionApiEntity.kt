package s2.bdd.repository

import org.assertj.core.api.Assertions

abstract class AssertionApiEntity<Entity, ID: Any, Asserter>: AssertionEntity<Entity, ID, Asserter> {
    override suspend fun exists(id: ID) {
        val entity = existsById(id)
        Assertions.assertThat(entity).isTrue
    }

    override suspend fun notExists(id: ID) {
        Assertions.assertThat(existsById(id)).isFalse
    }

    override suspend fun assertThatId(id: ID): Asserter {
        val entity = findById(id)
        Assertions.assertThat(entity).isNotNull
        return assertThat(entity!!)
    }

    private suspend fun existsById(id: ID): Boolean {
        return findById(id) != null
    }

    abstract override suspend fun assertThat(entity: Entity): Asserter
    abstract suspend fun findById(id: ID): Entity?
}
