package s2.spring.automate.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("s2")
data class S2AutomateProperties(
	val storming: S2AutomateStormingProperties
) {

	data class S2AutomateStormingProperties(
		val enable: Boolean
	)
}