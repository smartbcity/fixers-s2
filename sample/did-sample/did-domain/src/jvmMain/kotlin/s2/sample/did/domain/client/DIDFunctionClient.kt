package s2.sample.did.domain.client

import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.Protocol
import f2.client.ktor.get
import f2.dsl.fnc.F2Supplier
import kotlinx.coroutines.flow.flow

actual fun didClient(
    protocol: Protocol,
    host: String,
    port: Int,
    path: String?,
): F2Supplier<DIDFunctionClient> {
    return F2Supplier {
        flow {
            F2ClientBuilder.get(protocol, host, port, path).let { s2Client ->
                emit(DIDFunctionClient(s2Client))
            }
        }
    }
}
