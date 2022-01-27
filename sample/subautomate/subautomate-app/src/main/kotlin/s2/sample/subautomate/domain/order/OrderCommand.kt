package s2.sample.subautomate.domain.order

import s2.sourcing.dsl.Decide
import s2.dsl.automate.WithId
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2Event
import s2.dsl.automate.S2InitCommand
import s2.sample.subautomate.domain.OrderState
import s2.sample.subautomate.domain.model.OrderId

interface OrderBuyDecider : OrderDecide<OrderBuyCommand, OrderBoughtEvent>
interface OrderBuyCommand : OrderCommand
interface OrderBoughtEvent : OrderEvent

interface OrderSellDecider : OrderDecide<OrderSellCommand, OrderSoldEvent>
interface OrderSellCommand : OrderCommand
interface OrderSoldEvent : OrderEvent

interface OrderBurnDecider : OrderDecide<OrderBurnCommand, OrderBurnedEvent>
interface OrderBurnCommand : OrderCommand
interface OrderBurnedEvent : OrderEvent

interface OrderDecide<
		COMMAND : OrderCommand,
		EVENT : OrderEvent
		>
	: Decide<COMMAND, EVENT>

interface OrderEvent : S2Event<OrderState, OrderId>, WithId<OrderId>
interface OrderCommand : S2Command<OrderId>
interface OrderInitCommand : S2InitCommand

