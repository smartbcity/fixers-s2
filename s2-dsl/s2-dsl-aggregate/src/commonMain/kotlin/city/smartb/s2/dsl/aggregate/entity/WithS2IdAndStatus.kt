package city.smartb.s2.dsl.aggregate.entity

interface WithS2IdAndStatus<ID, STATE>: WithS2Id<ID>, WithS2State<STATE> {
}