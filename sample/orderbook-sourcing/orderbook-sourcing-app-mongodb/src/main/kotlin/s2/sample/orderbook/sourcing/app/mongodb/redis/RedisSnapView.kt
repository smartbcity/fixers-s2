package s2.sample.orderbook.sourcing.app.mongodb.redis

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.redis.lettucemod.api.StatefulRedisModulesConnection
import com.redis.lettucemod.search.CreateOptions
import com.redis.lettucemod.search.Field
import com.redis.lettucemod.search.SearchOptions
import f2.dsl.cqrs.page.OffsetPagination
import f2.dsl.cqrs.page.PageQueryResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component

@Component
class RedisSnapView(
    val searchConnection: StatefulRedisModulesConnection<String, String>,
    val objectMapper: ObjectMapper = jacksonObjectMapper().apply {
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
		setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
) {
	companion object {
		const val TYPE = "type"
	}

	final suspend inline fun <reified MODEL> get(id: String): MODEL? {
		return searchConnection.reactive().jsonGet(buildId<MODEL>(id), ".").awaitSingleOrNull()?.let { value ->
			objectMapper.readValue(value)
		}
	}

	final inline fun <reified MODEL> buildId(value: String) = "${MODEL::class.simpleName}-$value"


	final suspend inline fun <reified MODEL> save(id: String, entity: MODEL): MODEL {
		val json = objectMapper.valueToTree<ObjectNode>(entity).apply {
			put(TYPE, MODEL::class.simpleName)
		}.toString()
		searchConnection.reactive().jsonSet(buildId<MODEL>(id), "\$",  json).awaitSingle()
		return get(id)!!
	}

	final suspend inline fun <reified MODEL> remove(id: String): Boolean {
		return searchConnection.reactive().del(buildId<MODEL>(id)).awaitSingle() == 1L
	}

	final suspend inline fun <reified MODEL> createIndex(vararg fields: RedisIndexField) {
		val indexName = MODEL::class.simpleName
		try {
			searchConnection.reactive().indexInfo(indexName).awaitFirstOrNull()
		} catch (e: Exception) {
			val opt = CreateOptions.builder<String, String>()
				.on(CreateOptions.DataType.JSON)
				.prefix(indexName)
				.build()
			val field = fields.map { index ->
				val asName = index.name ?: index.field
				when(index.type) {
					Field.Type.TEXT -> Field.text("\$.${index.field}").`as`(asName).noStem().sortable().build()
					Field.Type.GEO -> Field.geo("\$.${index.field}").`as`(asName).build()
					Field.Type.NUMERIC -> Field.numeric("\$.${index.field}").`as`(asName).sortable().build()
					Field.Type.TAG -> Field.tag("\$.${index.field}").`as`(asName).build()
				}
			}
			searchConnection.reactive().create(
				indexName,
				opt,
				*field.toTypedArray(),
			).awaitFirstOrNull()
		}
	}

	final suspend inline fun <reified MODEL> createIndex() {
		val fields = MODEL::class.java.declaredFields
			.filter { it.name != "Companion" }
			.filter {
				it.type == String::class.java
			}
			.map { field ->
				RedisIndexField(field.name, Field.Type.TEXT)
			}
		createIndex<MODEL>(*fields.toTypedArray())
	}

	final suspend inline fun <reified MODEL> searchById(field: String, id: String): PageQueryResult<MODEL> {
		val searchOptions = SearchOptions.builder<String, String>()
		val queryByTag = id(field, id)

		val searchResult = searchConnection.reactive().search(MODEL::class.simpleName, queryByTag, searchOptions.build()).awaitSingle()
		val results = searchResult.map { document ->
			document.get("$")?.let {
				objectMapper.readValue<MODEL>(it)
			}
		}.filterNotNull()
		return PageQueryResult(
			total = searchResult.count.toInt(),
			items = results,
			pagination = OffsetPagination(
				limit = 0,
				offset = searchResult.count.toInt(),
			)
		)
	}

	final suspend inline fun <reified MODEL> search(query: String?, pagination: OffsetPagination?, sortBy: String?): PageQueryResult<MODEL> {
		val searchOptions = SearchOptions.builder<String, String>()

		val pp = pagination ?: OffsetPagination(offset = 0, limit = 10000)
		pp?.let {  SearchOptions.builder<String, String>()
			searchOptions.limit(SearchOptions.limit(pp.offset.toLong(), pp.limit.toLong()))
		}
		sortBy?.let {
			searchOptions.sortBy(SearchOptions.SortBy.asc(sortBy))
		}
		val queryWithType = "${query ?: "*"}"

		val searchResult = searchConnection.reactive().search(MODEL::class.simpleName, queryWithType, searchOptions.build()).awaitSingle()
		val results = searchResult.map { document ->
			document.get("$")?.let {
				objectMapper.readValue<MODEL>(it)
			}
		}.filterNotNull()
		return PageQueryResult(
			total = searchResult.count.toInt(),
			items = results,
			pagination = pagination ?: OffsetPagination(
				limit = 0,
				offset = searchResult.count.toInt(),
			)
		)
	}

	final suspend inline fun <reified MODEL> count(): Long {
		val queryWithType = "@type:${MODEL::class.simpleName}"
		return searchConnection.reactive()
			.search(MODEL::class.simpleName, queryWithType)
			.map { it.count }
			.awaitSingle()
	}

	final suspend inline fun <reified MODEL> all(): Flow<MODEL> {
		val queryWithType = "@type:${MODEL::class.simpleName}"
		val searchResult = searchConnection.reactive().search(MODEL::class.simpleName, queryWithType).awaitSingle()
		return searchResult.map { document ->
			document.get("$")?.let {
				objectMapper.readValue<MODEL>(it)
			}
		}.filterNotNull().asFlow()
	}
}

fun id(field: String, id: String): String {
	return "@${field}:{${id.replace("-", "\\-")}}"
}
