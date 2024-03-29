package aleh.ahiyevich.criminal

import aleh.ahiyevich.criminal.databinding.ActivityMainBinding
import aleh.ahiyevich.criminal.repository.DataBaseHelper
import aleh.ahiyevich.criminal.view.fragments.AuthorizationFragment
import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreferences

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() {
            return _binding!!
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = getPreferences(MODE_PRIVATE)

        val localAccessToken = sharedPref.getString(Constants.ACCESS_TOKEN, "")
        if (localAccessToken != null) {
            //запрос на проверку валидности токена
            DataBaseHelper().getAuthUser(localAccessToken, this, sharedPref, this,"1")
            //если все окей отправляю на страницу сезонов
        } else {
            // отправляем на страницу авторизации
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_for_fragment, AuthorizationFragment())
                .addToBackStack(null)
                .commit()

        }
    }


    //Зануляем байндинг
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // При пересоздании активити вызываем функцию ФуллСкрин
    override fun onStart() {
        super.onStart()
        callFullScreen()
    }

    // Обработка нажатия системной кнопки назад
    override fun onBackPressed() {
        exitDialog()
    }

    // Вызов полноэкранного режима
    private fun callFullScreen() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // для ранних версий API
            window.decorView.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            // для более поздних версий API
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

    // Создаем диалог для выхода из приложения
    private fun exitDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_exit, null)
        val myDialog = Dialog(this)

        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        dialogBinding.findViewById<Button>(R.id.alert_yes_exit_dialog).setOnClickListener {
            finish()
        }

        dialogBinding.findViewById<Button>(R.id.alert_no_exit_dialog).setOnClickListener {
            myDialog.dismiss()
        }
    }

}








