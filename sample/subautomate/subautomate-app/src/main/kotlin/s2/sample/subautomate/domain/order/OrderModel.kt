package s2.sample.subautomate.domain.order

import arrow.optics.optics
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.sample.subautomate.domain.orderBook.OrderBookId

typealias OrderId = String

@optics
data class Order(
	val id: OrderId,
	val orderBookId: OrderBookId,
	val state: OrderState
): WithS2Id<OrderId>, WithS2State<OrderState> {
	override fun s2Id() = id
	override fun s2State() = state
	companion object
}
