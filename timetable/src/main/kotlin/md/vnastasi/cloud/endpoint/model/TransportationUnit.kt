package md.vnastasi.cloud.endpoint.model

data class TransportationUnit(
    val number: String,
    val operator: String,
    val category: Category,
    val type: TransportationType
)
