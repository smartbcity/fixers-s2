package s2.dsl.automate.model

interface WithS2State<STATE> {
	fun s2State(): STATE
}