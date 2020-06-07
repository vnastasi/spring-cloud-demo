package md.vnastasi.cloud.exception

import md.vnastasi.cloud.client.model.error.ErrorResponseWrapper
import md.vnastasi.cloud.client.model.error.ErrorTypeWrapper
import md.vnastasi.cloud.client.model.error.ErrorWrapper

fun ErrorResponseWrapper.asApiError(): ApiError =
    errors.firstOrNull()?.asApiError()
        ?: ApiError(type = ApiErrorType.NS_SERVICE_FAILURE, message = "There was a problem contacting NS API portal")

private fun ErrorWrapper.asApiError(): ApiError =
    ApiError(type = type.asApiErrorType(), message = message)

private fun ErrorTypeWrapper.asApiErrorType(): ApiErrorType =
    when (this) {
        ErrorTypeWrapper.NO_DISRUPTIONS_FOUND_FOR_ID -> ApiErrorType.NO_DISRUPTIONS_FOUND_FOR_ID
    }
