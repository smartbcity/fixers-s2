//
//import f2.client.ktor.WS
//import s2.sample.did.domain.features.DidCreateCommand
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.asDeferred
//import kotlinx.coroutines.promise
//import kotlinx.serialization.InternalSerializationApi
//import kotlin.random.Random
//import kotlin.test.assertEquals
//
//class RSocketClientTest {
//
////	@Test
//	@InternalSerializationApi
//	fun shouldCreateDidByCreateDidCmd() = GlobalScope.promise {
//		val client = didClient(WS, "localhost", 7000, "rsocket").asDeferred().await()
//		val number = Random.nextInt(0, 99)
//		val id = "test${number}"
//		val tt = client.createDid(
//			DidCreateCommand(
//			id = id,
//		)
//		).asDeferred().await()
//		assertEquals(id, tt.id)
//	}
//
////	@Test
//	@InternalSerializationApi
//	fun shouldCreateDidByDidCreateCommandPayload() = GlobalScope.promise {
//		val client = didClient(WS, "localhost", 7000, "rsocket").asDeferred().await()
//		val number = Random.nextInt(100, 199)
//		val id = "test${number}"
//		val tt = client.createDid(
//			DidCreateCommand(
//			id = id,
//		)
//		).asDeferred().await()
//		assertEquals(id, tt.id)
//	}
//}