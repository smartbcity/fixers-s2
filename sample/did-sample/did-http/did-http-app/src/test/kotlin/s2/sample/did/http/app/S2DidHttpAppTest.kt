package s2.sample.did.http.app

import f2.client.ktor.HTTP
import f2.client.ktor.http.httpClientBuilder
import f2.function.spring.invokeSingle
import s2.sample.did.domain.client.DidAggregateClient
import s2.sample.did.domain.features.DidAddPublicKeyCommand
import s2.sample.did.domain.features.DidCreateCommand
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.cloud.function.context.FunctionCatalog
import org.springframework.test.context.junit.jupiter.SpringExtension
import s2.dsl.automate.S2Automate
import s2.dsl.automate.ssm.toSsm
import s2.sample.did.domain.didS2
import ssm.client.asAgent
import ssm.client.domain.Signer
import ssm.client.domain.SignerAdmin
import ssm.dsl.query.SsmListUserQuery
import ssm.dsl.query.SsmListUserQueryFunction
import ssm.f2.SsmCreateCommand
import ssm.f2.SsmCreateFunction
import java.util.*
import java.util.function.Function
import java.util.function.Supplier

@TestInstance(PER_CLASS)
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(initializers = [MongoContainerInitializer::class] )
class S2DidHttpAppTest {

	@LocalServerPort
	protected var port: Int = 8080

	@Autowired
	lateinit var ssmCreateFunction: SsmCreateFunction

	@Autowired
	lateinit var ssmListUserQueryFunction: SsmListUserQueryFunction

	@Autowired
	lateinit var signerAdmin: SignerAdmin
	@Autowired
	lateinit var signer: Signer

	@BeforeEach
	fun tt() = runBlocking<Unit> {
		val ssm = didS2().toSsm()
		val users = ssmListUserQueryFunction.invokeSingle(SsmListUserQuery(
			"http://localhost:9090", null
		))

		val cmd = SsmCreateCommand(
			baseUrl = "http://localhost:9090",
			signerAdmin = signerAdmin,
			ssm = ssm,
			agent = signer.asAgent()
		)
		ssmCreateFunction.invokeSingle(cmd)
	}

	@Test
	fun testBasicAggregateFnc() = runBlocking<Unit> {
		val id = UUID.randomUUID().toString()
		val client = httpClientBuilder().build("http", "localhost", port)
		val result: String = client.invoke("createDid", "[{\"id\":\"${id}\"}]")
		assertThat(result).isEqualTo("[{\"id\":\"${id}\",\"type\":{\"position\":0}}]")
	}

	@Test
	fun didAggregateClientTest() = runBlocking {
		val didAggregateClient = DidAggregateClient.get(protocol = HTTP, "localhost", port)
		val id = UUID.randomUUID().toString()
		val cmd = DidCreateCommand(
			id = id,
		)
		val created = didAggregateClient.createDid(cmd)

		val addPublicKey = DidAddPublicKeyCommand(
			id, "publickey-${UUID.randomUUID()}"
		)
		val added = didAggregateClient.addPublicKey(addPublicKey)

		println(created)
		println(added)
	}

	@Autowired
	lateinit var catalog: FunctionCatalog

	@Test
	fun testCatalogue() = runBlocking {
		val version = catalog.lookup<Any>("version")
		val createDid = catalog.lookup<Any>("createDid")
		val addPublicKey = catalog.lookup<Any>("addPublicKey")
		val revoke = catalog.lookup<Any>("revoke")
		val namesFunction = catalog.getNames(Function::class.java)
		val namesSupplier = catalog.getNames(Supplier::class.java)
		println(version)
		println(createDid)
		println(addPublicKey)
		println(revoke)
		println(namesFunction)
		println(namesSupplier)
	}

}