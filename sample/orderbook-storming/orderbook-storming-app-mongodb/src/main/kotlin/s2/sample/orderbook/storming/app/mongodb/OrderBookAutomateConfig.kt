package s2.sample.orderbook.storming.app.mongodb

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import s2.sample.subautomate.domain.OrderBookState
import s2.sample.subautomate.domain.model.OrderBook
import s2.sample.subautomate.domain.model.OrderBookId
import s2.sample.subautomate.domain.orderBook.OrderBookEvent
import s2.sample.subautomate.domain.orderBookAutomate
import s2.spring.automate.storming.S2AutomateEvolverSpring
import s2.spring.storming.data.S2StormingSpringDataAdapter

@Configuration
class OrderBookAutomateConfig : S2StormingSpringDataAdapter<
		OrderBook, OrderBookState, OrderBookEvent, OrderBookId, OrderBookS2Aggregate>() {
	override fun automate() = orderBookAutomate

	@Autowired
	lateinit var endableLoopS2Aggregate: OrderBookS2Aggregate

	override fun executor(): OrderBookS2Aggregate = endableLoopS2Aggregate
}

@Service
class OrderBookS2Aggregate : S2AutomateEvolverSpring<OrderBook, OrderBookState, OrderBookEvent, OrderBookId>()
