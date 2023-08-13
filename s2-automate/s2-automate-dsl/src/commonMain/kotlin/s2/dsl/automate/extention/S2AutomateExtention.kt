package s2.dsl.automate.extention

import s2.dsl.automate.Cmd
import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2State


inline fun <reified C: Cmd> S2Automate.canExecuteTransitionAnd(
    model: WithS2State<out S2State>, hasAccess: () -> Boolean
): Boolean {
    return isAvailableTransition(model, C::class.simpleName!!) && hasAccess()
}

fun S2Automate.isAvailableTransition(state: WithS2State<out S2State>?, type: String): Boolean {
    return state != null && isAvailableTransition(
        currentState = state.s2State(),
        type = type
    )
}

fun S2Automate.isAvailableTransition(currentState: S2State, type: String): Boolean {
    return getAvailableTransitions(currentState).any { it.result?.name == type || it.action.name == type }
}
