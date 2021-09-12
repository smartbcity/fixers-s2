//package s2.sample.did.http.app
//
//import f2.client.executeInvoke
//import f2.client.ktor.HTTP
//import f2.client.ktor.http.httpClientBuilder
//import f2.dsl.fnc.invoke
//import java.util.*
//import java.util.function.Function
//import java.util.function.Supplier
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.runBlocking
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestInstance
//import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
//import org.junit.jupiter.api.extension.ExtendWith
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.web.server.LocalServerPort
//import org.springframework.cloud.function.context.FunctionCatalog
//import org.springframework.test.context.ContextConfiguration
//import org.springframework.test.context.junit.jupiter.SpringExtension
//import s2.dsl.automate.ssm.toSsm
//import s2.sample.did.domain.client.didClient
//import s2.sample.did.domain.didS2
//import s2.sample.did.domain.features.DidAddPublicKeyCommand
//import s2.sample.did.domain.features.DidCreateCommand
//import s2.sample.did.domain.features.DidCreatedEvent
//import s2.sample.did.http.app.config.MongoContainerInitializer
//import ssm.chaincode.dsl.SsmChaincodeProperties
//import ssm.chaincode.dsl.query.SsmListUserQuery
//import ssm.chaincode.dsl.query.SsmListUserQueryFunction
//import ssm.chaincode.f2.SsmCreateFunction
//import ssm.sdk.sign.model.Signer
//import ssm.sdk.sign.model.SignerAdmin
//
//@TestInstance(PER_CLASS)
//@ExtendWith(SpringExtension::class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(initializers = [MongoContainerInitializer::class] )
//class S2DidHttpAppTest {
//
//	@LocalServerPort
//	protected var port: Int = 8080
//
//	@Autowired
//	lateinit var ssmCreateFunction: SsmCreateFunction
//
//	@Autowired
//	lateinit var ssmListUserQueryFunction: SsmListUserQueryFunction
//
//	@Autowired
//	lateinit var signerAdmin: SignerAdmin
//
//	@Autowired
//	lateinit var signer: Signer
//
//	@BeforeEach
//	fun tt() = runBlocking<Unit> {
//		val ssm = didS2().toSsm()
//		val users = ssmListUserQueryFunction.invoke(
//			SsmListUserQuery(
//				chaincode = SsmChaincodeProperties(
//					baseUrl = "http://localhost:$port",
//					channelId = null,
//					chaincodeId = null,
//				),
//				bearerToken = null,
//			)
//		)
//
////		val cmd = SsmCreateCommand(
////			baseUrl = "http://localhost:9090",
////			signerAdmin = signerAdmin,
////			ssm = ssm,
////			agent = signer.asAgent(),
////			channelId = null,
////			bearerToken = null,
////			chaincodeId = "sandbox_ssm",
////		)
////		ssmCreateFunction.invoke(cmd)
//	}
//
//	@Test
//	fun testBasicAggregateFnc() = runBlocking<Unit> {
//		val id = UUID.randomUUID().toString()
//		val client = httpClientBuilder().build("http", "localhost", port, null)
//		val result: DidCreatedEvent = client.executeInvoke("createDid", DidCreateCommand(
//			id = id
//		))
//		assertThat(result.id).isEqualTo(id)
//	}
//
//	@Test
//	fun didAggregateClientTest() = runBlocking {
//		val didAggregateClient = didClient(protocol = HTTP, "localhost", port)
//		val id = UUID.randomUUID().toString()
//		val cmd = DidCreateCommand(
//			id = id,
//		)
//		val created = didAggregateClient.invoke().first().createDid().invoke(cmd)
//
//		val addPublicKey = DidAddPublicKeyCommand(
//			id, "publickey-${UUID.randomUUID()}"
//		)
//		val added = didAggregateClient.invoke().first().addPublicKey().invoke(addPublicKey)
//
////		println(created)
////		println(added)
//	}
//
//	@Autowired
//	lateinit var catalog: FunctionCatalog
//
//	@Test
//	fun testCatalogue() = runBlocking {
//		val version = catalog.lookup<Any>("version")
//		val createDid = catalog.lookup<Any>("createDid")
//		val addPublicKey = catalog.lookup<Any>("addPublicKey")
//		val revoke = catalog.lookup<Any>("revoke")
//		val namesFunction = catalog.getNames(Function::class.java)
//		val namesSupplier = catalog.getNames(Supplier::class.java)
//		println(version)
//		println(createDid)
//		println(addPublicKey)
//		println(revoke)
//		println(namesFunction)
//		println(namesSupplier)
//	}
//
//}
