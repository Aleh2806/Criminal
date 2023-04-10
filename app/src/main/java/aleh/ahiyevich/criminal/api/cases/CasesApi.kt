package aleh.ahiyevich.retrofit.api.cases

import aleh.ahiyevich.criminal.api.cases.Cases
import retrofit2.Call
import retrofit2.http.*

// Интерфейс для отправки данных в БД, чтобы получить ServerResponse данные в ответ
interface CasesApi {

// Еденичный запрос к делам сезона №2
//    /get-cases-by-season?season_id=2

//Динамический запрос к делам сезона
    @GET("cases/get-cases-by-season?")
    fun getCases(
        @Header("Authorization") token: String,
        @Query("season_id") seasonId: Int
    ): Call<Cases>

    @GET("cases/get-one?id=9")
    fun getCase(@Header("Authorization") token: String): Call<Cases>


}