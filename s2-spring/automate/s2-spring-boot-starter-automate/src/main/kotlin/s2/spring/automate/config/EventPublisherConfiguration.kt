package s2.spring.automate.config

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.automate.persister.SpringEventPublisher

@Configuration
open class EventPublisherConfiguration {

	@Bean
	open fun eventPublisher(publisher: ApplicationEventPublisher): SpringEventPublisher {
		return SpringEventPublisher(publisher)
	}
}
