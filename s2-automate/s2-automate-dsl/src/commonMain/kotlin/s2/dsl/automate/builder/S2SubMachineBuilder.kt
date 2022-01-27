package s2.dsl.automate.builder

import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2Command
import kotlin.reflect.KClass

class S2SubMachineBuilder<ID> {
	lateinit var automate: S2Automate
	var startsOn: List<KClass<out S2Command<ID>>> = emptyList()
	var endsOn: List<KClass<out S2Command<ID>>> = emptyList()
	var autostart: Boolean = false
	var blocking: Boolean = false
	var singleton: Boolean = true
}
