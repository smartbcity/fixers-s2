package s2.dsl.automate.builder

import s2.dsl.automate.Msg
import s2.dsl.automate.S2State
import s2.dsl.automate.S2Transition

class S2NodeBuilder {
    lateinit var state: S2State
    val transactions: MutableList<S2Transition> = mutableListOf()

    inline fun <reified CMD : Msg> transaction(
        exec: S2NodeTransitionBuilder.() -> Unit,
    ) {
        val builder = S2NodeTransitionBuilder()
        builder.exec()
        S2Transition(
            from = state,
            to = builder.to ?: state,
            role = builder.role,
            msg = CMD::class,
        ).let(transactions::add)
    }
}
