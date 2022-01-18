package s2.dsl.automate.builder

import s2.dsl.automate.S2Role
import s2.dsl.automate.S2State

class S2TransitionBuilder {
	lateinit var from: S2State
	lateinit var to: S2State
	lateinit var role: S2Role
}

class S2NodeTransitionBuilder {
	var to: S2State? = null
	lateinit var role: S2Role
}

class S2SelfTransitionBuilder {
	val states = mutableListOf<S2State>()
	lateinit var role: S2Role
}

class S2InitTransitionBuilder<STATE: S2State> {
	lateinit var to: STATE
	lateinit var role: S2Role
}
