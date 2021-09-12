package s2.dsl.automate

import f2.dsl.cqrs.ErrorSeverity

actual interface S2Error {
	actual val severity: ErrorSeverity
	actual val type: String
	actual val description: String
	actual val date: String
	actual val payload: Map<String, String>
}
