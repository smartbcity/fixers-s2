package s2.spring.automate.ssm.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("s2")
data class S2SsmProperties(
	val ssm: S2SsmProperties
) {

	data class S2SsmProperties(
		val enable: Boolean,
	)
}