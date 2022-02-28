package s2.dsl.automate.builder

import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2Role
import s2.dsl.automate.S2State
import kotlin.reflect.KClass

class S2TransitionBuilder {
	var from: S2State? = null
	lateinit var to: S2State
	lateinit var role: S2Role
	@Deprecated("Useless, you can remove this line.")
	var cmd: KClass<out S2Command<*>>? = null
}

class S2NodeTransitionBuilder {
	var to: S2State? = null
	lateinit var role: S2Role
}

class S2SelfTransitionBuilder {
	val states = mutableListOf<S2State>()
	lateinit var role: S2Role
}

class S2InitTransitionBuilder {
	lateinit var to: S2State
	lateinit var role: S2Role
	@Deprecated("Useless, you can remove this line.")
	var cmd: KClass<out S2InitCommand>? = null
}
