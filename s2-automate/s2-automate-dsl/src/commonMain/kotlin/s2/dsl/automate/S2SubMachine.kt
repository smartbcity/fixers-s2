package s2.dsl.automate

import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KClass

@JsExport
@JsName("S2SubMachine")
open class S2SubMachine<ID>(
    open val automate: S2Automate<ID>,
    open val startsOn: List<KClass<out S2Command<ID>>>,
    open val endsOn: List<KClass<out S2Command<ID>>>,
    open val autostart: Boolean,
    open val blocking: Boolean,
    open val singleton: Boolean
)
