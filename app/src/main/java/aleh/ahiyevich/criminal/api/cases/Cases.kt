package aleh.ahiyevich.criminal.api.cases

data class Cases(
    val success: Boolean,
    val data: List<DataCase>
)

data class DataCase(
    val id: Int,
    val name: String,
    val image: String
)