package md.vnastasi.cloud.endpoint

import md.vnastasi.cloud.exception.ApiErrorType
import md.vnastasi.cloud.exception.ApiException
import org.springframework.boot.json.JsonParseException
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

private const val KEY_REASON = "reason"
private const val KEY_MESSAGE = "message"

@Component
class ApiErrorAttributes : DefaultErrorAttributes() {

    override fun getErrorAttributes(request: ServerRequest?, includeStackTrace: Boolean): MutableMap<String, Any> {
        val errorAttributes = super.getErrorAttributes(request, includeStackTrace)

        when (val throwable = getError(request)) {
            is ApiException -> {
                errorAttributes += KEY_REASON to throwable.error.type.name
                errorAttributes += KEY_MESSAGE to throwable.error.message
            }
            is JsonParseException -> {
                errorAttributes += KEY_REASON to ApiErrorType.UNPARSABLE_RESPONSE
                errorAttributes += KEY_REASON to throwable.message
            }
            else -> {
                errorAttributes += KEY_REASON to ApiErrorType.UNKNOWN_FAILURE
                errorAttributes += KEY_REASON to throwable.message
            }
        }
        return errorAttributes
    }
}
