package s2.dsl.automate.builder

import s2.dsl.automate.S2Role
import s2.dsl.automate.S2State
import kotlin.reflect.KClass
import s2.dsl.automate.Evt

class S2TransitionBuilder {
    var from: S2State? = null
	lateinit var to: S2State
	lateinit var role: S2Role
	var evt: KClass<out Evt>? = null
}

class S2NodeTransitionBuilder {
	var to: S2State? = null
	lateinit var role: S2Role
	var evt: KClass<out Evt>? = null
}

class S2SelfTransitionBuilder {
	val states = mutableListOf<S2State>()
	lateinit var role: S2Role
	var evt: KClass<out Evt>? = null
}

class S2InitTransitionBuilder {
	lateinit var to: S2State
	lateinit var role: S2Role
	var evt: KClass<out Evt>? = null
}
