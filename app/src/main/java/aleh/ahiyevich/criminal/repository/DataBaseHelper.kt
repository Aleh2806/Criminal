package aleh.ahiyevich.criminal.repository

import aleh.ahiyevich.criminal.Constants
import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.model.AuthUser
import aleh.ahiyevich.criminal.model.SeasonData
import aleh.ahiyevich.criminal.model.SeasonsDB
import aleh.ahiyevich.criminal.view.adapters.CrimesAdapter
import aleh.ahiyevich.criminal.view.adapters.SeasonsAdapter
import aleh.ahiyevich.criminal.view.fragments.AuthorizationFragment
import aleh.ahiyevich.criminal.view.fragments.SeasonsFragment
import aleh.ahiyevich.retrofit.api.auth.AuthApi
import aleh.ahiyevich.retrofit.api.auth.AuthRequest
import aleh.ahiyevich.retrofit.api.auth.RefreshTokenRequest
import aleh.ahiyevich.retrofit.api.auth.ResponseToken
import aleh.ahiyevich.criminal.api.cases.Cases
import aleh.ahiyevich.retrofit.api.cases.CasesApi
import aleh.ahiyevich.criminal.api.cases.DataCase
import aleh.ahiyevich.retrofit.api.seasons.SeasonsApi
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataBaseHelper {

    private lateinit var sharedPref: SharedPreferences
    // Авторизация пользователя
    fun login(
        context: Context, email: String, password: String, activity: AppCompatActivity
    ) {
        if (email.isEmpty()) {
            Toast.makeText(context, "Введите е-маил", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(context, "Введите пароль", Toast.LENGTH_SHORT).show()
        } else {
            val request = BaseRequest().retrofit.create(AuthApi::class.java)
            val call: Call<ResponseToken> = request.login(AuthRequest(email, password))
            call.enqueue(object : Callback<ResponseToken> {
                override fun onResponse(
                    call: Call<ResponseToken>,
                    response: Response<ResponseToken>
                ) {
                    if (response.isSuccessful) {
                        saveTokens(
                            response.body()!!.data.access_token,
                            response.body()!!.data.refresh_token
                        ,activity)
                        Toast.makeText(
                            context,
                            "Авторизация успешная\n\nAccess token = ${response.body()!!.data.access_token}\n\n" +
                                    "Refresh token = ${response.body()!!.data.refresh_token}",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        activity
                            .supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container_for_fragment,SeasonsFragment())
                            .commit()
                    } else {
                        Toast.makeText(context, "Ошибка авторизации", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseToken>, t: Throwable) {
                    Toast.makeText(context, "Ошибка авторизации", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    // Получение авторизованного пользовталя
    fun getAuthUser(
        token: String,
        context: Context,
        sharedPref: SharedPreferences,
        activity: AppCompatActivity,
    ) {

        val request = BaseRequest().retrofit.create(AuthApi::class.java)
        val call: Call<AuthUser> = request.getAuthUser("Bearer $token")
        call.enqueue(object : Callback<AuthUser> {
            override fun onResponse(call: Call<AuthUser>, response: Response<AuthUser>) {
                val authUser = response.body()
                if (response.isSuccessful) {
                    if (authUser?.success == true){
                        // на страницу сезонов
                        activity
                            .supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container_for_fragment,SeasonsFragment())
                            .commit()
                        Log.d("Getauthuser","token check")
                    } else {
                        // На страницу авторизации
                        activity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container_for_fragment,AuthorizationFragment())
                            .commit()
                    }
                } else if (response.code() == 401) {
                    getAuthByRefreshToken(context, sharedPref, activity)
                } else {
                    activity
                        .supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container_for_fragment,AuthorizationFragment())
                        .commit()
                }

            }

            override fun onFailure(call: Call<AuthUser>, t: Throwable) {
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    // Получение сезонов
    fun getSeasons(adapter: SeasonsAdapter, seasonList: ArrayList<SeasonData>, token: String, context: Context){

            val request = BaseRequest().retrofit.create(SeasonsApi::class.java)
            val call = request.getSeasons("Bearer $token")
            call.enqueue(object : Callback<SeasonsDB> {
                override fun onResponse(call: Call<SeasonsDB>, response: Response<SeasonsDB>) {
                    val data = response.body()!!.data
                    for (i in data){
                        seasonList.add(i)

                    }
                    adapter.notifyDataSetChanged()

                    Toast.makeText(context, "Season get", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<SeasonsDB>, t: Throwable) {
                    Toast.makeText(context, "Ошибка загрузки, проверьте интернет соединение", Toast.LENGTH_SHORT).show()

                }
            })
    }

    // Получение дел сезона
    fun getCases(adapter: CrimesAdapter, crimesList: ArrayList<DataCase>, token: String, context: Context, seasonId: Int) {

            val request = BaseRequest().retrofit.create(CasesApi::class.java)
            val call = request.getCases("Bearer $token", seasonId)
            call.enqueue(object : Callback<Cases> {
                override fun onResponse(call: Call<Cases>, response: Response<Cases>) {
                    val data = response.body()!!.data
                    for (i in data){
                        crimesList.add(i)
                    }
                    adapter.notifyDataSetChanged()
                    Toast.makeText(context, "Cases gets", Toast.LENGTH_SHORT).show()

                }

                override fun onFailure(call: Call<Cases>, t: Throwable) {
                    Toast.makeText(context, "Ошибка загрузки, проверьте интернет соединение", Toast.LENGTH_SHORT).show()
                }
            })
    }

    // Получение Рефреш токена, если Access token мертв
    fun getAuthByRefreshToken(context: Context, sharedPref: SharedPreferences, activity: AppCompatActivity) {
        val refreshToken = sharedPref.getString(Constants.REFRESH_TOKEN, "")
        if (refreshToken != null) {
            val request = BaseRequest().retrofit.create(AuthApi::class.java)
            val call: Call<ResponseToken> =
                request.getAuthByRefreshToken(RefreshTokenRequest(refreshToken))
            call.enqueue(object : Callback<ResponseToken> {
                override fun onResponse(
                    call: Call<ResponseToken>,
                    response: Response<ResponseToken>
                ) {
                    if (response.code() == 401) {
                        // На страницу авторизации
                        activity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container_for_fragment,AuthorizationFragment())
                            .commit()

                    } else {
                        saveTokens(
                            response.body()!!.data.access_token,
                            response.body()!!.data.refresh_token,
                            activity
                        )
                        // НА страницу сезонов
                        activity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container_for_fragment,SeasonsFragment())
                            .commit()
                    }
                }

                override fun onFailure(call: Call<ResponseToken>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

            })
        } else {
            // На страницу Авторизации
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_for_fragment,AuthorizationFragment())
                .commit()
        }
    }

    // Сохранение токена в Shared Preferences
    fun saveTokens(accessToken: String, refreshToken: String, activity: Activity) {
        sharedPref = activity.getPreferences(AppCompatActivity.MODE_PRIVATE)
        val mEditor: SharedPreferences.Editor = sharedPref.edit()
        mEditor.putString(Constants.ACCESS_TOKEN, accessToken)
        mEditor.putString(Constants.REFRESH_TOKEN, refreshToken)
        mEditor.apply()
    }

}