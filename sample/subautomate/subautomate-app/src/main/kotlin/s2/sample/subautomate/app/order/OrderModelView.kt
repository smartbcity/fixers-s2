package s2.sample.subautomate.app.order

import s2.sample.subautomate.domain.order.Order
import s2.sample.subautomate.domain.order.OrderBoughtEvent
import s2.sample.subautomate.domain.order.OrderBurnedEvent
import s2.sample.subautomate.domain.order.OrderEvent
import s2.sample.subautomate.domain.order.OrderSoldEvent
import s2.sourcing.dsl.view.View

class OrderModelView : View<OrderEvent, Order> {

	override suspend fun evolve(event: OrderEvent, model: Order?): Order? = when (event) {
		is OrderBoughtEvent -> TODO()
		is OrderSoldEvent -> TODO()
		is OrderBurnedEvent -> TODO()
	}
}
