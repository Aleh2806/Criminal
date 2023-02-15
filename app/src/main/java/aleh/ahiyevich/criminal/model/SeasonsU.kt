package aleh.ahiyevich.criminal.model

data class SeasonsU(
    var imageSeason: String = "",
    var openSeason: Boolean = false,
    var nameSeason: String = ""
)

data class CrimesU(
    var imageCrime: String = "",
    var nameCrime: String = "",
    var openCrime: Boolean = false
)

data class Materials(
    var photo: String = ""
)
