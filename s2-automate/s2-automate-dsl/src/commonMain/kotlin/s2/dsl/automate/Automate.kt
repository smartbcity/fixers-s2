package s2.dsl.automate

interface Automate<ID> {
	fun getAvailableTransitions(state: S2State): List<S2Transition<ID>>
	fun isAvailableTransition(currentState: S2State, command: S2Command<ID>): Boolean
	fun isAvailableInitTransition(command: S2InitCommand): Boolean
	fun isFinalState(state: S2State): Boolean
	fun isSameState(from: S2State?, to: S2State): Boolean
}
