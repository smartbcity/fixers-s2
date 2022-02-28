package s2.sample.subautomate.app.order

import org.springframework.stereotype.Service
import s2.sample.subautomate.app.config.OrderS2AutomateDecider
import s2.sample.subautomate.domain.OrderDecider
import s2.sample.subautomate.domain.order.OrderBoughtEvent
import s2.sample.subautomate.domain.order.OrderBurnCommand
import s2.sample.subautomate.domain.order.OrderBurnedEvent
import s2.sample.subautomate.domain.order.OrderBuyCommand
import s2.sample.subautomate.domain.order.OrderSellCommand
import s2.sample.subautomate.domain.order.OrderSoldEvent
import s2.sourcing.dsl.Decide

@Service
class OrderDeciderImpl(
	private val aggregate: OrderS2AutomateDecider
) : OrderDecider {
	override fun orderBuy(): Decide<OrderBuyCommand, OrderBoughtEvent> {
		TODO("Not yet implemented")
	}

	override fun orderSell(): Decide<OrderSellCommand, OrderSoldEvent> {
		TODO("Not yet implemented")
	}

	override fun orderBurn(): Decide<OrderBurnCommand, OrderBurnedEvent> {
		TODO("Not yet implemented")
	}
}
