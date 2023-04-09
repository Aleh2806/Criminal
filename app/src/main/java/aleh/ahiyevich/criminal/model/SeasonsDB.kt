package aleh.ahiyevich.criminal.model

data class SeasonsDB(
    val success: Boolean,
    val data: ArrayList<SeasonData>
)

data class SeasonData(
    val id: Int,
    val name: String,
    val image: String,
    val created_at: String,
    val updated_at: String
)