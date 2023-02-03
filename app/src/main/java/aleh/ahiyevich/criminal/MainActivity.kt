package aleh.ahiyevich.criminal

import aleh.ahiyevich.criminal.databinding.ActivityMainBinding
import aleh.ahiyevich.criminal.model.Crimes
import aleh.ahiyevich.criminal.view.fragments.*
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_authorisation.*
import kotlinx.android.synthetic.main.fragment_crimes_list.*


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() {
            return _binding!!
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_for_fragment, CrimesFragment())
                .commit()
        }
    }


    //Зануляем байндинг
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // При пересоздании активити вызываем функцию ФуллСкрин
    override fun onResume() {
        super.onResume()
        callFullScreen()
    }

    // Обработка нажатия системной кнопки назад
    override fun onBackPressed() {
        exitDialog()
    }

    // Проверка на первый запуск приложения
    private fun checkFirstStart() {
        val sp: SharedPreferences = getSharedPreferences("hasVisited", Context.MODE_PRIVATE)
        val hasVisited: Boolean = sp.getBoolean("hasVisited", false)

        if (!hasVisited) {
            // Сработает, если Вход в приложение первый
            // Ставим метку,что вход уже был
            val e: SharedPreferences.Editor = sp.edit()
            e.putBoolean("hasVisited", true)
            e.apply()

            // Запускаем Активити/Фрагмент который нужнен при Первом входе
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_for_fragment, AuthorisationFragment())
                .commit()
        } else {
            // Срабатывает, если вход в приложение уже был
            // Запускаем Активити/Фрагмент который нужнен при Последующих входах
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_for_fragment, CrimesFragment())
                .commit()
        }
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








