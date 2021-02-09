package city.smartb.s2.dsl.aggregate.event

interface EventPublisher {
	fun <EVENT> publish(event: EVENT)
}