package aleh.ahiyevich.retrofit.api.seasons

import aleh.ahiyevich.criminal.model.SeasonsDB
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

// Интерфейс для отправки данных в БД, чтобы получить ServerResponse данные в ответ
interface SeasonsApi {

    @GET("seasons/get-all")
    fun getSeasons(@Header ("Authorization") token: String): Call<SeasonsDB>
}