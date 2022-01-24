package s2.dsl.automate

import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KClass

@JsExport
@JsName("S2SubMachine")
open class S2SubMachine<ID>(
    open val automate: S2Automate<ID>,
    open val startsOn: List<KClass<out S2Command<ID>>> = emptyList(),
    open val endsOn: List<KClass<out S2Command<ID>>> = emptyList(),
    open val autostart: Boolean = false,
    open val blocking: Boolean = false,
    open val singleton: Boolean = false
)
