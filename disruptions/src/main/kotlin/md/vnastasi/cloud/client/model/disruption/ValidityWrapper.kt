package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class ValidityWrapper(

    @JsonProperty("startDatum")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    val startDate: OffsetDateTime,

    @JsonProperty("eindDatum")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    val endDate: OffsetDateTime
)
