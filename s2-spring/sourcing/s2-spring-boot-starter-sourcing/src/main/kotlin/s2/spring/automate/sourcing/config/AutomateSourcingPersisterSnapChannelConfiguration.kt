package s2.spring.automate.sourcing.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.automate.sourcing.AutomateSourcingPersisterSnapChannel

@Configuration
open class AutomateSourcingPersisterSnapChannelConfiguration {

	@Bean
	open fun automateSourcingPersisterSnapChannel(): AutomateSourcingPersisterSnapChannel {
		return AutomateSourcingPersisterSnapChannel()
	}
}
