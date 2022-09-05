package s2.dsl.automate.model

import kotlin.js.JsExport

@JsExport
interface WithS2IdAndStatus<ID, STATE> : WithS2Id<ID>, WithS2State<STATE>
