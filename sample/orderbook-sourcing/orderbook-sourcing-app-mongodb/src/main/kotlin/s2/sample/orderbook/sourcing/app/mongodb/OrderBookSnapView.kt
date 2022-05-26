package s2.sample.orderbook.sourcing.app.mongodb

import com.redis.lettucemod.api.StatefulRedisModulesConnection
import com.redis.lettucemod.search.Field
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import s2.sample.orderbook.sourcing.app.mongodb.redis.RedisIndexField
import s2.sample.orderbook.sourcing.app.mongodb.redis.RedisSnapView
import s2.sample.subautomate.domain.model.OrderBook
import s2.sourcing.dsl.snap.SnapRepository
import javax.annotation.PostConstruct

@Component
class OrderBookSnapView(
    searchConnection: StatefulRedisModulesConnection<String, String>,
): SnapRepository<OrderBook, String>  {

    @PostConstruct
    fun init() = runBlocking {
        redisSnapView.createIndex<OrderBook>(
            RedisIndexField(OrderBook::id.name, Field.Type.TAG)
        )
    }

    private val redisSnapView = RedisSnapView(searchConnection)

    override suspend fun get(id: String): OrderBook? {
        return redisSnapView.get<OrderBook>(id)
    }

    override suspend fun save(entity: OrderBook): OrderBook {
        return redisSnapView.save(entity.id, entity)
    }

    override suspend fun remove(id: String): Boolean {
        return redisSnapView.remove<OrderBook>(id)
    }
}

fun String?.default(default: String) = this ?: default
