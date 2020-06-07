package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class ValidityWrapper(

    @JsonProperty("startDatum")
    val startDate: OffsetDateTime,

    @JsonProperty("eindDatum")
    val endDate: OffsetDateTime
)
