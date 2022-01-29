package s2.sample.subautomate.domain

import s2.sample.subautomate.domain.order.OrderBoughtEvent
import s2.sample.subautomate.domain.order.OrderBurnCommand
import s2.sample.subautomate.domain.order.OrderBurnedEvent
import s2.sample.subautomate.domain.order.OrderBuyCommand
import s2.sample.subautomate.domain.order.OrderDecide
import s2.sample.subautomate.domain.order.OrderSellCommand
import s2.sample.subautomate.domain.order.OrderSoldEvent
import s2.sample.subautomate.domain.orderBook.OrderBookCloseCommand
import s2.sample.subautomate.domain.orderBook.OrderBookClosedEvent
import s2.sample.subautomate.domain.orderBook.OrderBookCreateCommand
import s2.sample.subautomate.domain.orderBook.OrderBookCreatedEvent
import s2.sample.subautomate.domain.orderBook.OrderBookDecide
import s2.sample.subautomate.domain.orderBook.OrderBookPublishCommand
import s2.sample.subautomate.domain.orderBook.OrderBookPublishedEvent
import s2.sample.subautomate.domain.orderBook.OrderBookUpdateCommand
import s2.sample.subautomate.domain.orderBook.OrderBookUpdatedEvent

interface OrderBookDecider {
	fun orderBookCreateDecider(): OrderBookDecide<OrderBookCreateCommand, OrderBookCreatedEvent>
	fun orderBookUpdateDecider(): OrderBookDecide<OrderBookUpdateCommand, OrderBookUpdatedEvent>
	fun orderBookPublishDecider(): OrderBookDecide<OrderBookPublishCommand, OrderBookPublishedEvent>
	fun orderBookCloseDecider(): OrderBookDecide<OrderBookCloseCommand, OrderBookClosedEvent>

	fun orderBuy(): OrderDecide<OrderBuyCommand, OrderBoughtEvent>
	fun orderSell(): OrderDecide<OrderSellCommand, OrderSoldEvent>
	fun orderBurn(): OrderDecide<OrderBurnCommand, OrderBurnedEvent>
}
