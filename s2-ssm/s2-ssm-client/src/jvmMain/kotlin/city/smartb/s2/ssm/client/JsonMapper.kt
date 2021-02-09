package city.smartb.s2.ssm.client

interface JsonMapper {
	fun <T> encodeToString(entity: T): String
}