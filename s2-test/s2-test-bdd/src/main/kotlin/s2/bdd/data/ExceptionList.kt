package s2.bdd.data

import kotlin.reflect.KClass

class ExceptionList {
    private val exceptions = mutableListOf<Exception>()

    val list: List<Exception>
        get() = exceptions.toList()

    fun add(e: Exception) {
        exceptions.add(e)
    }

    @Suppress("UNCHECKED_CAST")
    fun <E: Exception> lastOfType(klass: KClass<E>): E? {
        return exceptions.lastOrNull(klass::isInstance) as E?
    }

    fun <E: Exception> filterIsInstance(klass: KClass<E>): List<E> {
        return list.filterIsInstance(klass.java)
    }

    fun reset() {
        exceptions.clear()
    }
}
