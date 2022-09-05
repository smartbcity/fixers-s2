package s2.dsl.automate.builder

import s2.dsl.automate.Msg
import s2.dsl.automate.S2Automate
import kotlin.reflect.KClass

class S2SubMachineBuilder {
	lateinit var automate: S2Automate
	var startsOn: List<KClass<out Msg>> = emptyList()
	var endsOn: List<KClass<out Msg>> = emptyList()
	var autostart: Boolean = false
	var blocking: Boolean = false
	var singleton: Boolean = true
}
