package aleh.ahiyevich.criminal.model

data class SeasonsDB(
    val success: Boolean,
    val data: ArrayList<SeasonData>
)

data class SeasonData(
    val name: String,
    val mobile_image: String
)