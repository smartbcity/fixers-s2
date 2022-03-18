package s2.sample.subautomate.app.config

import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import s2.sample.subautomate.app.orderbook.OrderBookModelView
import s2.sample.subautomate.domain.orderBook.OrderBook
import s2.sample.subautomate.domain.orderBook.OrderBookEvent
import s2.sample.subautomate.domain.orderBook.OrderBookId
import s2.sample.subautomate.domain.orderBook.OrderBookState
import s2.sample.subautomate.domain.orderBook.orderBookAutomate
import s2.spring.automate.sourcing.S2AutomateDeciderSpring
import s2.spring.sourcing.data.S2SourcingSpringDataAdapter

@Configuration
class OrderBookAutomateConfig(orderBookS2Aggregate: OrderBookS2Aggregate) : S2SourcingSpringDataAdapter<
		OrderBook, OrderBookState, OrderBookEvent, OrderBookId, OrderBookS2Aggregate>(orderBookS2Aggregate, OrderBookModelView()) {
	override fun automate() = orderBookAutomate

}

@Service
class OrderBookS2Aggregate : S2AutomateDeciderSpring<OrderBook, OrderBookState, OrderBookEvent, OrderBookId>()
