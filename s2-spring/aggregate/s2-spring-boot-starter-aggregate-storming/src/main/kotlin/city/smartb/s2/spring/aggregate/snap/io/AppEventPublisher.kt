package city.smartb.s2.spring.aggregate.snap.io

import city.smartb.s2.dsl.aggregate.event.EventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.PayloadApplicationEvent
import org.springframework.stereotype.Component

@Component
class AppEventPublisher(
		private val applicationEventPublisher: ApplicationEventPublisher
): EventPublisher {
	override fun <EVENT> publish(event: EVENT) {
		applicationEventPublisher.publishEvent(PayloadApplicationEvent(this, event))
	}
}