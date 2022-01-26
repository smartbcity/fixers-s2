package s2.dsl.automate.event.storming.automate

import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("S2StormingSubMachine")
open class S2StormingSubMachine<ID>(
	open val automate: S2StormingAutomate<ID>,
)
