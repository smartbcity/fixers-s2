package s2.dsl.automate.model

interface WithS2IdAndStatus<ID, STATE> : WithS2Id<ID>, WithS2State<STATE>
