package aleh.ahiyevich.retrofit.api.auth

// Классы для принятия данных при ответе сервера
data class ResponseToken(
    val success: Boolean,
    val data: DataTokens
)

data class DataTokens(
    val access_token: String,
    val refresh_token: String
)

