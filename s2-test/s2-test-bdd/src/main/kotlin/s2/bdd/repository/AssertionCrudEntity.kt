package s2.bdd.repository

import kotlinx.coroutines.reactor.awaitSingle
import org.assertj.core.api.Assertions
import org.springframework.data.repository.reactive.ReactiveCrudRepository

abstract class AssertionCrudEntity<Entity, ID: Any, Asserter> {
    protected abstract val repository: ReactiveCrudRepository<Entity, ID>

    suspend fun exists(id: ID) {
        val entity = existsById(id)
        Assertions.assertThat(entity).isTrue
    }

    suspend fun notExists(id: ID) {
        Assertions.assertThat(existsById(id)).isFalse
    }

    private suspend fun existsById(id: ID): Boolean {
        return repository.existsById(id).awaitSingle()
    }

    suspend fun assertThatId(id: ID): Asserter {
        exists(id)
        val entity = repository.findById(id).awaitSingle()
        return assertThat(entity)
    }

    abstract suspend fun assertThat(entity: Entity): Asserter
}
