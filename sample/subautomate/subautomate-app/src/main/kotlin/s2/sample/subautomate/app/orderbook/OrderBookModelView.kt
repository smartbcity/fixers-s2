package s2.sample.subautomate.app.orderbook

import s2.sample.subautomate.domain.orderBook.OrderBookState
import s2.sample.subautomate.domain.orderBook.OrderBook
import s2.sample.subautomate.domain.orderBook.OrderBookClosedEvent
import s2.sample.subautomate.domain.orderBook.OrderBookCreatedEvent
import s2.sample.subautomate.domain.orderBook.OrderBookEvent
import s2.sample.subautomate.domain.orderBook.OrderBookPublishedEvent
import s2.sample.subautomate.domain.orderBook.OrderBookUpdatedEvent
import s2.sample.subautomate.domain.orderBook.name
import s2.sample.subautomate.domain.orderBook.status
import s2.sourcing.dsl.view.View

class OrderBookModelView: View<OrderBookEvent, OrderBook> {

	override suspend fun evolve(event: OrderBookEvent, model: OrderBook?): OrderBook? = when(event) {
		is OrderBookCreatedEvent -> created(event)
		is OrderBookClosedEvent -> closed(model, event)
		is OrderBookPublishedEvent -> published(model, event)
		is OrderBookUpdatedEvent -> updated(model, event)
	}

	private fun closed(entity: OrderBook?, event: OrderBookClosedEvent): OrderBook? = entity?.let { current ->
		OrderBook.status.set(current, event.state)
	}

	private fun published(entity: OrderBook?, event: OrderBookPublishedEvent): OrderBook? = entity?.let { current ->
		OrderBook.status.set(current, event.state)
	}

	private fun updated(entity: OrderBook?, event: OrderBookUpdatedEvent): OrderBook? = entity?.let { current ->
		OrderBook.name.set(current, event.name)
	}

	private fun created(event: OrderBookCreatedEvent): OrderBook {
		return OrderBook(
			id = event.id,
			name = event.name,
			status = OrderBookState.Created
		)
	}
}
