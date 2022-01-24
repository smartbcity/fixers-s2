package s2.sample.subautomate.domain.orderBook

import s2.automate.storming.Decide
import s2.automate.storming.Evolve
import s2.dsl.automate.WithId
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2Event
import s2.dsl.automate.S2InitCommand
import s2.sample.subautomate.domain.OrderBookState
import s2.sample.subautomate.domain.model.OrderBook
import s2.sample.subautomate.domain.model.OrderBookId
import s2.sample.subautomate.domain.model.OrderId


data class OrderBookCreateCommand(
	val name: String
) : S2InitCommand

data class OrderBookCreatedEvent(
	val name: String,
	override val id: OrderId,
	override val type: OrderBookState
) : OrderBookEvent

data class OrderBookUpdateCommand(
	val name: String,
	override val id: OrderBookId
) : OrderBookCommand

data class OrderBookUpdatedEvent(
	val name: String,
	override val id: OrderId,
	override val type: OrderBookState
) : OrderBookEvent

data class OrderBookPublishCommand(override val id: OrderBookId) : OrderBookCommand
data class OrderBookPublishedEvent(
	override val id: OrderId, override val type: OrderBookState
) : OrderBookEvent

data class OrderBookCloseCommand(override val id: OrderBookId) : OrderBookCommand
data class OrderBookClosedEvent(
	override val id: OrderId, override val type: OrderBookState
) : OrderBookEvent

sealed interface OrderBookEvent : S2Event<OrderBookState, OrderId>, WithId<OrderId>
sealed interface OrderBookCommand : S2Command<OrderBookId>
interface OrderBookInitCommand : S2InitCommand


typealias OrderBookDecide<COMMAND, EVENT> = Decide<OrderBookId, OrderBookState, COMMAND, EVENT>
//fun interface OrderBookDecide<
//		COMMAND : Command,
//		EVENT : OrderBookEvent>
//	: Decide<OrderBookId, OrderBookState, COMMAND, EVENT>

fun interface OrderBookEvolve<EVENT : OrderBookEvent> : Evolve<OrderBook, EVENT>
