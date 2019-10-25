package md.vnastasi.cloud.exception

data class ApiError(
    val type: ApiErrorType,
    val message: String
)

enum class ApiErrorType {

    UNKNOWN_FAILURE,
    UNPARSABLE_RESPONSE,
    NS_SERVICE_FAILURE,
    UNKNOWN_STATION,
    NO_DEPARTURES_FOR_STATION,
    NO_ARRIVALS_FOR_STATION
}
