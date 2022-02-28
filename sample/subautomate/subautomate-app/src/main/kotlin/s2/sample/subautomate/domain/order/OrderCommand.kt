package s2.sample.subautomate.domain.order

import s2.dsl.automate.Cmd
import s2.dsl.automate.Evt
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.sample.subautomate.domain.order.OrderState
import s2.sample.subautomate.domain.order.OrderId
import s2.sourcing.dsl.Decide
import s2.dsl.automate.S2Command
import s2.sample.subautomate.domain.orderBook.OrderBookEvent

data class OrderBuyCommand(override val id: OrderId) : OrderCommand
data class OrderBoughtEvent(val id: OrderId, val state: OrderState) : OrderEvent {
	override fun s2Id() = id
	override fun s2State() = state
}

data class OrderSellCommand(override val id: OrderId) : OrderCommand
data class OrderSoldEvent(val id: OrderId, val state: OrderState) : OrderEvent {
	override fun s2Id() = id
	override fun s2State() = state
}


data class OrderBurnCommand(override val id: OrderId) : OrderCommand
data class OrderBurnedEvent(val id: OrderId, val state: OrderState) : OrderEvent {
	override fun s2Id() = id
	override fun s2State() = state
}

sealed interface OrderEvent : Evt, WithS2Id<OrderId>, WithS2State<OrderState>
interface OrderCommand : S2Command<OrderId>
interface OrderInitCommand : S2InitCommand
