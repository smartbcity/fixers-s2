package city.smartb.s2.spring.aggregate.io

import city.smartb.s2.dsl.aggregate.event.EventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.PayloadApplicationEvent
import org.springframework.stereotype.Component

@Component
class EventPublisher(
		private val applicationEventPublisher: ApplicationEventPublisher
): EventPublisher {
	override fun <EVENT> publish(event: EVENT) {
		applicationEventPublisher.publishEvent(PayloadApplicationEvent(this, event))
	}
}