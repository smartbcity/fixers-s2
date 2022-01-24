package s2.sample.subautomate.app

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import s2.sample.subautomate.domain.OrderBookState
import s2.sample.subautomate.domain.orderBook.OrderBookCloseCommand
import s2.sample.subautomate.domain.orderBook.OrderBookClosedEvent
import s2.sample.subautomate.domain.orderBook.OrderBookCreateCommand
import s2.sample.subautomate.domain.orderBook.OrderBookCreatedEvent
import s2.sample.subautomate.domain.orderBook.OrderBookDecide
import s2.sample.subautomate.domain.orderBook.OrderBookDecider
import s2.sample.subautomate.domain.orderBook.OrderBookPublishCommand
import s2.sample.subautomate.domain.orderBook.OrderBookPublishedEvent
import s2.sample.subautomate.domain.orderBook.OrderBookUpdateCommand
import s2.sample.subautomate.domain.orderBook.OrderBookUpdatedEvent
import java.util.UUID

@Service
class OrderBookDeciderImpl(
	private val aggregate: OrderBookS2Aggregate
) : OrderBookDecider {

	@Bean
	override fun orderBookCreateDecider(): OrderBookDecide<OrderBookCreateCommand, OrderBookCreatedEvent> =
		aggregate.init { cmd ->
			OrderBookCreatedEvent(id = UUID.randomUUID().toString(), name = cmd.name, type = OrderBookState.Created)
		}

	@Bean
	override fun orderBookUpdateDecider(): OrderBookDecide<OrderBookUpdateCommand, OrderBookUpdatedEvent> =
		aggregate.decide { cmd, _ ->
			OrderBookUpdatedEvent(id = cmd.id, name = cmd.name, type = OrderBookState.Created)
		}

	@Bean
	override fun orderBookPublishDecider(): OrderBookDecide<OrderBookPublishCommand, OrderBookPublishedEvent> =
		aggregate.decide { cmd, entity ->
			OrderBookPublishedEvent(
				id = cmd.id,
				type = entity.status
			)
		}

	@Bean
	override fun orderBookCloseDecider(): OrderBookDecide<OrderBookCloseCommand, OrderBookClosedEvent> =
		aggregate.decide { cmd, _ ->
			OrderBookClosedEvent(
				id = cmd.id,
				type = OrderBookState.Closed
			)
		}
}

