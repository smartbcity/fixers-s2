package s2.dsl.automate

import kotlin.js.JsName

expect interface WithId<ID> {
	@JsName("id")
	val id: ID
}
