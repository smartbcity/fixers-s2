package city.smartb.s2.dsl.aggregate.exception

class EntityNotFoundException(
		val id: String,
) : Exception(
		"entity not found with $id")