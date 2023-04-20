package aleh.ahiyevich.criminal.api.directories

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DirectoriesApi {

    @GET("cases/get-one?")
    fun getDirectoryName(
        @Header("Authorization") token: String,
        @Query("id") crimeId: Int
    ): Call<Data>
}