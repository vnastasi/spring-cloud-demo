package md.vnastasi.cloud.endpoint

import md.vnastasi.cloud.exception.ApiErrorType
import md.vnastasi.cloud.exception.ApiException
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.core.codec.DecodingException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import java.io.IOException

const val KEY_REASON = "reason"
const val KEY_MESSAGE = "message"

@Component
class ApiErrorAttributes : DefaultErrorAttributes() {

    override fun getErrorAttributes(request: ServerRequest?, options: ErrorAttributeOptions?): MutableMap<String, Any> {
        val errorAttributes = super.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE))

        when (val throwable = getError(request)) {
            is ApiException -> {
                errorAttributes += KEY_REASON to throwable.error.type.name
                errorAttributes += KEY_MESSAGE to throwable.error.message
            }
            is DecodingException -> {
                errorAttributes += KEY_REASON to ApiErrorType.UNPARSABLE_RESPONSE
                errorAttributes += KEY_MESSAGE to throwable.message
            }
            is IOException -> {
                errorAttributes += KEY_REASON to ApiErrorType.NS_SERVICE_FAILURE
                errorAttributes += KEY_MESSAGE to throwable.message
            }
            else -> {
                errorAttributes += KEY_REASON to ApiErrorType.UNKNOWN_FAILURE
                errorAttributes += KEY_MESSAGE to throwable.message
            }
        }

        return errorAttributes
    }
}
