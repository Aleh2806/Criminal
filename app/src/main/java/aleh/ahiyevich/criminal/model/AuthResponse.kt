package aleh.ahiyevich.criminal.model

data class AuthUser(
    val success: Boolean,
    val data: UserData
)

data class UserData(
    val user: DetailsUser
)

data class DetailsUser(
    val id: Int,
    val login: String,
    val email: String,
    val name: String,
)