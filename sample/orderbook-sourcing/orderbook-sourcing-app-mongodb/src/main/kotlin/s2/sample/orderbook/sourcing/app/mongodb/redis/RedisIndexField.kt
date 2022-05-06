package s2.sample.orderbook.sourcing.app.mongodb.redis

import com.redis.lettucemod.search.Field

class RedisIndexField (
	val field: String,
	val type: Field.Type,
	val name: String? = null,
)
