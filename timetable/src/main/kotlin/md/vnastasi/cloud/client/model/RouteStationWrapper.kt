package md.vnastasi.cloud.client.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RouteStationWrapper(

    @JsonProperty("uicCode")
    val uicCode: String,

    @JsonProperty("mediumName")
    val name: String
)
