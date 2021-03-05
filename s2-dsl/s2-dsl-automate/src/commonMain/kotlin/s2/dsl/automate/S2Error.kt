package s2.dsl.automate

import f2.dsl.cqrs.Error
import f2.dsl.cqrs.ErrorSeverity
import f2.dsl.cqrs.ErrorSeverityError
import f2.dsl.cqrs.ErrorSeverityWarning
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("S2Error")
interface S2Error : Error<Map<String, String>> {
	companion object
}

@JsExport
@JsName("S2ErrorBase")
class S2ErrorBase(
	override val severity: ErrorSeverity,
	override val type: String,
	override val description: String,
	override val date: String,
	override val payload: Map<String, String>
) : S2Error {

}

fun S2Error.Companion.error(code: String, description: String, payload: Map<String, String>): S2ErrorBase {
	return S2ErrorBase(
		severity = ErrorSeverityError(),
		type = code,
		description = description,
		date = "",
		payload = payload
	)
}

fun S2Error.Companion.warning(code: String, description: String, payload: Map<String, String>): S2ErrorBase {
	return S2ErrorBase(
		severity = ErrorSeverityWarning(),
		type = code,
		description = description,
		date = "",
		payload = payload
	)
}