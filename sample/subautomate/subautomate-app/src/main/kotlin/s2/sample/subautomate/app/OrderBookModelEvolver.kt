package s2.sample.subautomate.app

import org.springframework.stereotype.Service
import s2.automate.storming.event.Evolver
import s2.sample.subautomate.domain.OrderBookState
import s2.sample.subautomate.domain.model.OrderBook
import s2.sample.subautomate.domain.model.name
import s2.sample.subautomate.domain.model.status
import s2.sample.subautomate.domain.orderBook.OrderBookClosedEvent
import s2.sample.subautomate.domain.orderBook.OrderBookCreatedEvent
import s2.sample.subautomate.domain.orderBook.OrderBookEvent
import s2.sample.subautomate.domain.orderBook.OrderBookPublishedEvent
import s2.sample.subautomate.domain.orderBook.OrderBookUpdatedEvent

@Service
class OrderBookModelEvolver: Evolver<OrderBook, OrderBookEvent> {

	override suspend fun evolve(model: OrderBook?, event: OrderBookEvent): OrderBook? = when(event) {
		is OrderBookCreatedEvent -> created(event)
		is OrderBookClosedEvent -> closed(model, event)
		is OrderBookPublishedEvent -> published(model, event)
		is OrderBookUpdatedEvent -> updated(model, event)
	}

	private fun closed(entity: OrderBook?, event: OrderBookClosedEvent): OrderBook? = entity?.let { current ->
		OrderBook.status.set(current, event.type)
	}

	private fun published(entity: OrderBook?, event: OrderBookPublishedEvent): OrderBook? = entity?.let { current ->
		OrderBook.status.set(current, event.type)
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
