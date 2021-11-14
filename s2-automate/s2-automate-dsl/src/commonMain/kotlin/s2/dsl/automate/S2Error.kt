package s2.dsl.automate

import f2.dsl.cqrs.error.ErrorSeverity
import f2.dsl.cqrs.error.ErrorSeverityError
import f2.dsl.cqrs.error.ErrorSeverityWarning
import kotlin.js.JsExport
import kotlin.js.JsName

expect interface S2Error {
	val severity: ErrorSeverity
	val type: String
	val description: String
	val date: String
	val payload: Map<String, String>
}

@JsExport
@JsName("S2ErrorBase")
class S2ErrorBase(
	override val severity: ErrorSeverity,
	override val type: String,
	override val description: String,
	override val date: String,
	override val payload: Map<String, String>,
) : S2Error {

	override fun toString(): String {
		return "S2ErrorBase(severity=$severity, type='$type', description='$description', date='$date', payload=$payload)"
	}
}

fun s2error(code: String, description: String, payload: Map<String, String>): S2ErrorBase {
	return S2ErrorBase(
		severity = ErrorSeverityError(),
		type = code,
		description = description,
		date = "",
		payload = payload
	)
}

fun s2warning(code: String, description: String, payload: Map<String, String>): S2ErrorBase {
	return S2ErrorBase(
		severity = ErrorSeverityWarning(),
		type = code,
		description = description,
		date = "",
		payload = payload
	)
}
