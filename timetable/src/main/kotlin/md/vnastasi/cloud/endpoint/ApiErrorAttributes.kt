package md.vnastasi.cloud.endpoint

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.stereotype.Component

const val KEY_REASON = "reason"
const val KEY_MESSAGE = "message"

@Component
class ApiErrorAttributes : DefaultErrorAttributes() {

//    override fun getErrorAttributes(request: ServerRequest?, includeStackTrace: Boolean): MutableMap<String, Any> {
//        val errorAttributes = super.getErrorAttributes(request, includeStackTrace)
//
//        when (val throwable = getError(request)) {
//            is ApiException -> {
//                errorAttributes += KEY_REASON to throwable.error.type.name
//                errorAttributes += KEY_MESSAGE to throwable.error.message
//            }
//            is DecodingException -> {
//                errorAttributes += KEY_REASON to ApiErrorType.UNPARSABLE_RESPONSE
//                errorAttributes += KEY_MESSAGE to throwable.message
//            }
//            is IOException -> {
//                errorAttributes += KEY_REASON to ApiErrorType.NS_SERVICE_FAILURE
//                errorAttributes += KEY_MESSAGE to throwable.message
//            }
//            else -> {
//                errorAttributes += KEY_REASON to ApiErrorType.UNKNOWN_FAILURE
//                errorAttributes += KEY_MESSAGE to throwable.message
//            }
//        }
//        return errorAttributes
//    }
}
