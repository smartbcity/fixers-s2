package s2.sample.subautomate.app.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import s2.sample.subautomate.app.orderbook.OrderBookModelView
import s2.sample.subautomate.domain.orderBook.OrderBookState
import s2.sample.subautomate.domain.orderBook.OrderBook
import s2.sample.subautomate.domain.orderBook.OrderBookEvent
import s2.sample.subautomate.domain.orderBook.OrderBookId
import s2.sample.subautomate.domain.orderBook.orderBookAutomate
import s2.sourcing.dsl.view.View
import s2.spring.automate.sourcing.S2AutomateDeciderSpring
import s2.spring.sourcing.data.S2SourcingSpringDataAdapter

@Configuration
class OrderBookAutomateConfig(orderBookS2Aggregate: OrderBookS2Aggregate) : S2SourcingSpringDataAdapter<
		OrderBook, OrderBookState, OrderBookEvent, OrderBookId, OrderBookS2Aggregate>(orderBookS2Aggregate) {
	override fun automate() = orderBookAutomate

	@Bean
	override fun view(): View<OrderBookEvent, OrderBook> = OrderBookModelView()
}

@Service
class OrderBookS2Aggregate : S2AutomateDeciderSpring<OrderBook, OrderBookState, OrderBookEvent, OrderBookId>()
