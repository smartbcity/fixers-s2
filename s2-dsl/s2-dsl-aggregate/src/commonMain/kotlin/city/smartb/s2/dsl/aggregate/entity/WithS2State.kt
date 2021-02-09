package city.smartb.s2.dsl.aggregate.entity

interface WithS2State<STATE> {
	fun s2State(): STATE
}