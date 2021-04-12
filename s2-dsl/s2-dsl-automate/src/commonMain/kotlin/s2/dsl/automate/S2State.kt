package s2.dsl.automate

import kotlin.js.JsExport
import kotlin.js.JsName


@JsExport
@JsName("S2State")
interface S2State {
	@JsName("position")
	val position: Int

	@JsName("nodePosition")
	fun nodePosition() = position

}