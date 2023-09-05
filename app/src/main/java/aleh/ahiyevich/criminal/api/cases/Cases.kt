package aleh.ahiyevich.criminal.api.cases

data class Cases(
    val success: Boolean,
    val data: List<DataCase>
)

data class DataCase(
    val id: Int,
    val name: String,
    val original_name: String,
    val description: String,
    val case_steeps: List<Video>
)

data class Video(
    val id: Int,
    val original_name: String
)