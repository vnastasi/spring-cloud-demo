package md.vnastasi.cloud.client.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ProductWrapper(

    @JsonProperty("number")
    val number: String,

    @JsonProperty("categoryCode")
    val categoryCode: String,

    @JsonProperty("shortCategoryName")
    val shortCategoryName: String,

    @JsonProperty("longCategoryName")
    val longCategoryName: String,

    @JsonProperty("operatorCode")
    val operatorCode: String,

    @JsonProperty("operatorName")
    val operatorName: String,

    @JsonProperty("type")
    val type: ProductTypeWrapper
)
