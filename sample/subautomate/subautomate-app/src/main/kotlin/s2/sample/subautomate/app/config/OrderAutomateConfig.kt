package s2.sample.subautomate.app.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import s2.sample.subautomate.app.order.OrderModelView
import s2.sample.subautomate.domain.order.Order
import s2.sample.subautomate.domain.order.OrderEvent
import s2.sample.subautomate.domain.order.OrderId
import s2.sample.subautomate.domain.order.OrderState
import s2.sample.subautomate.domain.orderBook.orderBookAutomate
import s2.sourcing.dsl.view.View
import s2.spring.automate.sourcing.S2AutomateDeciderSpring
import s2.spring.sourcing.data.S2SourcingSpringDataAdapter

@Configuration
class OrderAutomateConfig : S2SourcingSpringDataAdapter<
		Order, OrderState, OrderEvent, OrderId, OrderS2AutomateDecider>() {
	override fun automate() = orderBookAutomate

	@Autowired
	lateinit var orderS2Aggregate: OrderS2AutomateDecider

	override fun executor(): OrderS2AutomateDecider = orderS2Aggregate

	@Bean
	override fun view(): View<OrderEvent, Order> = OrderModelView()
}

@Service
class OrderS2AutomateDecider : S2AutomateDeciderSpring<Order, OrderState, OrderEvent, OrderId>()
