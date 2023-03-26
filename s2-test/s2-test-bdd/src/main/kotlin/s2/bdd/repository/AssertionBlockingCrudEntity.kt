package s2.bdd.repository

import org.assertj.core.api.Assertions
import org.springframework.data.repository.CrudRepository

abstract class AssertionBlockingCrudEntity<Entity, ID: Any, Asserter> {
    protected abstract val repository: CrudRepository<Entity, ID>

    suspend fun exists(id: ID) {
        val entity = existsById(id)
        Assertions.assertThat(entity).isTrue
    }

    suspend fun notExists(id: ID) {
        Assertions.assertThat(existsById(id)).isFalse
    }

    private suspend fun existsById(id: ID): Boolean {
        return repository.existsById(id)
    }

    suspend fun assertThatId(id: ID): Asserter {
        exists(id)
        val entity = repository.findById(id).get()
        return assertThat(entity)
    }

    abstract suspend fun assertThat(entity: Entity): Asserter
}
