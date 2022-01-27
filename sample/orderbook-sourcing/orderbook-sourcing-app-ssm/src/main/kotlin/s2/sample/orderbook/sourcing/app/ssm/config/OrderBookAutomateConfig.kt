package s2.sample.orderbook.sourcing.app.ssm.config

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import s2.sample.subautomate.domain.OrderBookState
import s2.sample.subautomate.domain.model.OrderBook
import s2.sample.subautomate.domain.model.OrderBookId
import s2.sample.subautomate.domain.orderBook.OrderBookEvent
import s2.sample.subautomate.domain.orderBookAutomate
import s2.spring.automate.sourcing.S2AutomateEvolverSpring
import s2.spring.sourcing.ssm.PolymorphicEnumSerializer
import s2.spring.sourcing.ssm.S2StormingSsmAdapter
import ssm.chaincode.dsl.model.Agent
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.chaincode.dsl.model.uri.from
import ssm.sdk.sign.extention.loadFromFile
import kotlin.reflect.KClass

@Configuration
class OrderBookAutomateConfig : S2StormingSsmAdapter<
		OrderBook, OrderBookState, OrderBookEvent, OrderBookId, OrderBookS2Aggregate>() {
	override fun automate() = orderBookAutomate

	@Autowired
	lateinit var endableLoopS2Aggregate: OrderBookS2Aggregate

	override fun executor(): OrderBookS2Aggregate = endableLoopS2Aggregate

	override fun entityType(): KClass<OrderBookEvent> {
		return OrderBookEvent::class
	}

	override fun json(): Json = Json {
		serializersModule = SerializersModule {
			polymorphic(OrderBookState::class, PolymorphicEnumSerializer(OrderBookState.serializer()))
		}
	}

	override fun chaincodeUri(): ChaincodeUri {
		return ChaincodeUri.from(
			channelId = "sandbox",
			chaincodeId = "ssm",
		)
	}

	override fun signerAgent(): Agent {
		return Agent.loadFromFile("ssm-admin","user/ssm-admin")
	}
}

@Service
class OrderBookS2Aggregate : S2AutomateEvolverSpring<OrderBook, OrderBookState, OrderBookEvent, OrderBookId>()
