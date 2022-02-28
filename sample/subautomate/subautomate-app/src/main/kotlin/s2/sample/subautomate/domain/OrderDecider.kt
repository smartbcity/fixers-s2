package s2.sample.subautomate.domain

import s2.sample.subautomate.domain.order.OrderBoughtEvent
import s2.sample.subautomate.domain.order.OrderBurnCommand
import s2.sample.subautomate.domain.order.OrderBurnedEvent
import s2.sample.subautomate.domain.order.OrderBuyCommand
import s2.sample.subautomate.domain.order.OrderSellCommand
import s2.sample.subautomate.domain.order.OrderSoldEvent
import s2.sourcing.dsl.Decide


interface OrderDecider {
	fun orderBuy(): Decide<OrderBuyCommand, OrderBoughtEvent>
	fun orderSell(): Decide<OrderSellCommand, OrderSoldEvent>
	fun orderBurn(): Decide<OrderBurnCommand, OrderBurnedEvent>
}
