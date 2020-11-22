package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class DisturbanceWrapper(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("type")
    val type: DisturbanceTypeWrapper,

    @JsonProperty("header")
    val header: String,

    @JsonProperty("verwachting")
    val expectation: String?,

    @JsonProperty("oorzaak")
    val cause: String?,

    @JsonProperty("alternatiefVervoer")
    val alternativeTransport: String?,

    @JsonProperty("meldtijd")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    val reportedAt: OffsetDateTime?,

    @JsonProperty("trajecten")
    val trajectories: List<TrajectoryWrapper>?,

    @JsonProperty("extraReistijd")
    val extraTravelTime: String?,

    @JsonProperty("reisadviezen")
    val travelAdvice: TravelAdviceWrapper?,

    @JsonProperty("geldigheidsLijst")
    val validityList: List<ValidityWrapper>?,

    @JsonProperty("gevolg")
    val consequence: String?,

    @JsonProperty("periode")
    val period: String?
)
