package aleh.ahiyevich.criminal.repository

import aleh.ahiyevich.criminal.Constants
import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.api.cases.Cases
import aleh.ahiyevich.criminal.api.cases.DataCase
import aleh.ahiyevich.criminal.api.directories.Data
import aleh.ahiyevich.criminal.api.directories.DirectoriesApi
import aleh.ahiyevich.criminal.api.directories.DocumentDescription
import aleh.ahiyevich.criminal.model.AuthUser
import aleh.ahiyevich.criminal.model.SeasonData
import aleh.ahiyevich.criminal.model.SeasonsDB
import aleh.ahiyevich.criminal.view.adapters.CrimesAdapter
import aleh.ahiyevich.criminal.view.adapters.DescriptionsDetailsAdapter
import aleh.ahiyevich.criminal.view.adapters.DetailsAdapter
import aleh.ahiyevich.criminal.view.adapters.SeasonsAdapter
import aleh.ahiyevich.criminal.view.fragments.AuthorizationFragment
import aleh.ahiyevich.criminal.view.fragments.CrimesFragment
import aleh.ahiyevich.criminal.view.fragments.SeasonsFragment
import aleh.ahiyevich.retrofit.api.auth.AuthApi
import aleh.ahiyevich.retrofit.api.auth.AuthRequest
import aleh.ahiyevich.retrofit.api.auth.RefreshTokenRequest
import aleh.ahiyevich.retrofit.api.auth.ResponseToken
import aleh.ahiyevich.retrofit.api.cases.CasesApi
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
    fun login(context: Context, email: String, password: String, activity: AppCompatActivity) {
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
    fun getAuthUser(token: String, context: Context, sharedPref: SharedPreferences, activity: AppCompatActivity, numberSeason: String) {

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
                            .replace(R.id.container_for_fragment,SeasonsFragment.newInstance(numberSeason))
                            .addToBackStack(null)
                            .commit()
                        Log.d("@@@","token check")
                    } else {
                        // На страницу авторизации
                        activity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container_for_fragment,AuthorizationFragment())
                            .commit()
                    }
                } else if (response.code() == 401) {
                    getAuthByRefreshToken(sharedPref, activity)
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

    // Получение Рефреш токена, если Access token мертв
    fun getAuthByRefreshToken(sharedPref: SharedPreferences, activity: AppCompatActivity) {
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
                    activity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container_for_fragment,AuthorizationFragment())
                        .commit()
                    Log.d("refreshDead",t.message.toString())
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


    // Получение сезонов
    fun getSeasonsList(adapter: SeasonsAdapter, seasonList: ArrayList<SeasonData>, token: String, context: Context){

            val request = BaseRequest().retrofit.create(SeasonsApi::class.java)
            val call = request.getSeasons("Bearer $token")
            call.enqueue(object : Callback<SeasonsDB> {
                override fun onResponse(call: Call<SeasonsDB>, response: Response<SeasonsDB>) {
                    val data = response.body()!!.data
                    for (i in data){
                        seasonList.add(i)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<SeasonsDB>, t: Throwable) {
                    Toast.makeText(context, "Ошибка загрузки, проверьте интернет соединение", Toast.LENGTH_SHORT).show()
                }
            })
    }

    // Получение дел сезона
    fun getCasesList(adapter: CrimesAdapter, crimesList: ArrayList<DataCase>, token: String, context: Context, seasonId: Int) {

            val request = BaseRequest().retrofit.create(CasesApi::class.java)
            val call = request.getCases("Bearer $token", seasonId)
            call.enqueue(object : Callback<Cases> {
                override fun onResponse(call: Call<Cases>, response: Response<Cases>) {
                    val data = response.body()!!.data
                    for (i in data){
                        crimesList.add(i)
                    }
                    adapter.notifyDataSetChanged()
               }

                override fun onFailure(call: Call<Cases>, t: Throwable) {
                    Toast.makeText(context, "Ошибка загрузки, проверьте интернет соединение", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getDirectoryCrimesName(token: String, crimeId:Int, listDetails: ArrayList<String>, adapter: DetailsAdapter){
        val request = BaseRequest().retrofit.create(DirectoriesApi::class.java)
        val call = request.getDirectoryName("Bearer $token", crimeId)
        call.enqueue(object: Callback<Data>{
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
               val directories = response.body()!!.data.directories
                for (i in directories){
                    listDetails.add(i.directory.name)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.d("getDirectoryCrimesName", "onFailure: ${t.message.toString()}")
            }

        })
    }


    fun getMaterialsCrime(token: String, crimeId:Int, materialsList: ArrayList<DocumentDescription>,adapter: DescriptionsDetailsAdapter){
        val request = BaseRequest().retrofit.create(DirectoriesApi::class.java)
        val call = request.getDirectoryName("Bearer $token", crimeId)
        call.enqueue(object: Callback<Data>{
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
               val data = response.body()!!.data.directories
                for (i in data){
                    val documents = i.documents
                    for (it in documents){
                        materialsList.add(it.document)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.d("getDirectoryCrimesName", "onFailure: ${t.message.toString()}")
            }

        })
    }
}