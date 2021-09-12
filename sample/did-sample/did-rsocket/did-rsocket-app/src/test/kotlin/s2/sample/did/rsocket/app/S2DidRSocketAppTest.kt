//package s2.sample.did.rsocket.app
//
//import f2.client.ktor.WS
//import s2.sample.did.domain.client.DidAggregateClient
//import s2.sample.did.domain.features.DidCreateCommand
//import kotlinx.coroutines.runBlocking
//import org.assertj.core.api.Assertions
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestInstance
//import org.junit.jupiter.api.extension.ExtendWith
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.messaging.rsocket.RSocketRequester
//import org.springframework.test.context.junit.jupiter.SpringExtension
//import java.net.URI
//import java.util.*
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ExtendWith(SpringExtension::class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class S2DidRSocketAppTest {
//
//
//	//	@Test
//	fun didAggregateClientTest() = runBlocking<Unit> {
//		val didAggregateClient = DidAggregateClient.get(protocol = WS, "localhost", 7000, "rsocket")
//
//		val id = UUID.randomUUID().toString()
//		val cmd = DidCreateCommand(
//			id = id,
//		)
//		val test = didAggregateClient.createDid(cmd)
//		Assertions.assertThat(test.id).isEqualTo(id)
//		println(test)
//	}
//
//
//}
