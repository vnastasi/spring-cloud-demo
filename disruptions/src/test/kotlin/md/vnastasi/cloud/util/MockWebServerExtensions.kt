package md.vnastasi.cloud.util

import md.vnastasi.cloud.util.JsonUtils.readString
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

fun MockWebServer.enqueue(block: MockWebServerSpec.() -> Unit) {
    val spec = MockWebServerSpec().apply(block)
    val mockResponse = MockResponse()
        .setResponseCode(spec.status.value())
        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .setBody(readString(spec.body))
    enqueue(mockResponse)
}

class MockWebServerSpec(
    var status: HttpStatus = HttpStatus.OK,
    var body: String = "empty_wrapped.json"
)
