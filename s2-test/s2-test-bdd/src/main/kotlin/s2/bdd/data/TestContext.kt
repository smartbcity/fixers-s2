package s2.bdd.data

import s2.bdd.auth.AuthedUser
import f2.dsl.cqrs.Event
import org.springframework.stereotype.Component

typealias TestContextKey = String

interface BddContext {
    fun reset()
    fun resetEnv()
    fun <K: Any, V> testEntities(name: String): TestEntities<K, V>

    fun authedUser(): AuthedUser?
    fun errors(): ExceptionList
    fun events(): MutableList<Event>
}

@Component
open class TestContext: BddContext {
    protected val entityLists = mutableMapOf<String, TestEntities<*, *>>()

    protected var authedUser: AuthedUser? = null

    protected val errors = ExceptionList()
    protected val events = mutableListOf<Event>()

    override fun <K: Any, V> testEntities(name: String): TestEntities<K, V> = TestEntities<K, V>(name)
        .also {
            entityLists[name] to it
        }

    override fun authedUser() = authedUser
    override fun errors() = errors
    override fun events() = events

    override fun reset() {
        resetEnv()
        entityLists.values.forEach(TestEntities<*, *>::reset)
        errors.reset()
        events.clear()
    }

    override fun resetEnv() = Unit
}
