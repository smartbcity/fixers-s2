package s2.sample.subautomate.domain.order

import s2.dsl.automate.Cmd
import s2.dsl.automate.Evt
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.sample.subautomate.domain.OrderState
import s2.sample.subautomate.domain.model.OrderId
import s2.sourcing.dsl.Decide

data class OrderBuyCommand(val id: OrderId) : OrderCommand
data class OrderBoughtEvent(val id: OrderId, val state: OrderState) : OrderEvent {
	override fun s2Id() = id
	override fun s2State() = state
}

data class OrderSellCommand(val id: OrderId) : OrderCommand
data class OrderSoldEvent(val id: OrderId, val state: OrderState) : OrderEvent {
	override fun s2Id() = id
	override fun s2State() = state
}


data class OrderBurnCommand(val id: OrderId) : OrderCommand
data class OrderBurnedEvent(val id: OrderId, val state: OrderState) : OrderEvent {
	override fun s2Id() = id
	override fun s2State() = state
}



typealias OrderDecide<COMMAND, EVENT> = Decide<COMMAND, EVENT>

interface OrderEvent : Evt, WithS2Id<OrderId>, WithS2State<OrderState>
interface OrderCommand : Cmd
interface OrderInitCommand : S2InitCommand

