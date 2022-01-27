package s2.sample.subautomate.domain.orderBook

interface OrderBookEvolverFunction {
	fun orderBookCreateEvolve(): OrderBookEvolve<OrderBookCreatedEvent>
	fun orderBookUpdateEvolve(): OrderBookEvolve<OrderBookUpdatedEvent>
	fun orderBookPublishEvolve(): OrderBookEvolve<OrderBookPublishedEvent>
	fun orderBookCloseEvolve(): OrderBookEvolve<OrderBookClosedEvent>
}
