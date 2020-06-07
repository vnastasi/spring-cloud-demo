package md.vnastasi.cloud.exception

data class ApiError(
    val type: ApiErrorType,
    val message: String
)

enum class ApiErrorType {

    UNKNOWN_FAILURE,
    UNPARSABLE_RESPONSE,
    NO_DISRUPTIONS_FOUND_FOR_ID,
    NS_SERVICE_FAILURE
}
