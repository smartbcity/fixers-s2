package s2.sample.did.domain

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

class DidS2Test {

    @Test
    fun testS2JsonSerialization() {
        val json = Json.encodeToString(didS2())
        println(json)
    }

}
