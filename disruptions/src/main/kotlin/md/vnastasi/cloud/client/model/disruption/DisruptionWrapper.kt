package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty
import md.vnastasi.cloud.client.model.DisruptionTypeWrapper

data class DisruptionWrapper(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("type")
    val type: DisruptionTypeWrapper,

    @JsonProperty("header")
    val header: String,

    @JsonProperty("gevolg")
    val consequence: String,

    @JsonProperty("periode")
    val period: String,

    @JsonProperty("extraReistijd")
    val extraTravelTime: String,

    @JsonProperty("reisadviezen")
    val travelAdvices: TravelAdviceListWrapper,

    @JsonProperty("geldigheidsLijst")
    val validityList: List<ValidityWrapper>,

    @JsonProperty("trajecten")
    val sections: List<SectionWrapper>
)
