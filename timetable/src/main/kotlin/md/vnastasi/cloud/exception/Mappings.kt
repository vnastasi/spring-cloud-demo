package md.vnastasi.cloud.exception

import md.vnastasi.cloud.client.model.ErrorResponseWrapper
import md.vnastasi.cloud.client.model.ErrorTypeWrapper
import md.vnastasi.cloud.client.model.ErrorWrapper

fun ErrorResponseWrapper.asApiError(): ApiError =
    errors.firstOrNull()?.asApiError()
        ?: ApiError(type = ApiErrorType.NS_SERVICE_FAILURE, message = "There was a problem contacting NS API portal")

private fun ErrorWrapper.asApiError(): ApiError =
    ApiError(type = type.asApiErrorType(), message = message)

private fun ErrorTypeWrapper.asApiErrorType(): ApiErrorType =
    when (this) {
        ErrorTypeWrapper.STATION_NOT_FOUND -> ApiErrorType.UNKNOWN_STATION
        ErrorTypeWrapper.NO_DEPARTURES_FOUND_FOR_STATION -> ApiErrorType.NO_DEPARTURES_FOR_STATION
    }
