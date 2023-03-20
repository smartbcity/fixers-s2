package s2.bdd.data

class TestEntityIds<ID: Any> {
    private val mutableIds = mutableSetOf<ID>()

    val ids: Set<ID>
        get() = mutableIds.toSet()

    var lastCreated: ID? = null
        private set

    operator fun contains(id: ID) = mutableIds.contains(id)

    operator fun plusAssign(id: ID) {
        mutableIds += id
        lastCreated = id
    }

    fun add(id: ID) {
        this += id
    }

    fun addAll(ids: List<ID>) {
        ids.forEach(::add)
    }
}
