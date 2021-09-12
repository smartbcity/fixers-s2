package s2.spring.automate.ssm.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import ssm.sdk.sign.model.Signer
import ssm.sdk.sign.model.SignerAdmin

@ConstructorBinding
@ConfigurationProperties("s2")
data class S2SsmProperties(
	val ssm: S2SsmProperties,
) {

	data class S2SsmProperties(
		val baseUrl: String,
		val signer: S2SsmSignerProperties,
		val enable: Boolean,
	)

	data class S2SsmSignerProperties(
		val user: S2SsmSignerKeyProperties,
		val admin: S2SsmSignerKeyProperties,
	)

	data class S2SsmSignerKeyProperties(
		val name: String,
		val key: String,
	)

	fun getSignerAdmin(): SignerAdmin {
		val signer = Signer.loadFromFile(
			ssm.signer.admin.name,
			ssm.signer.admin.key
		)
		return SignerAdmin(
			signer.name,
			signer.pair
		)
	}

	fun getSigner(): Signer {
		return Signer.loadFromFile(
			ssm.signer.admin.name,
			ssm.signer.admin.key
		)
	}
}
