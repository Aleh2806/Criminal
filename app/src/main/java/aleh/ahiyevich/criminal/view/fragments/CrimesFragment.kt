package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentCrimesListBinding
import aleh.ahiyevich.criminal.model.CrimesU
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.repository.FireBaseHelper
import aleh.ahiyevich.criminal.view.adapters.CrimesAdapter
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment

class CrimesFragment : Fragment(), OnItemClick {

    private val fireBaseHelper = FireBaseHelper()
    private val crimesList: ArrayList<CrimesU> = ArrayList()
    private val adapterCrimes = CrimesAdapter(crimesList, this)
    private lateinit var numberSeason: String


    private var _binding: FragmentCrimesListBinding? = null
    private val binding: FragmentCrimesListBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrimesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCarouselViewCrimes()
        numberSeason = arguments?.getString("KEY_SEASON").toString()
        if (arguments == null){
            fireBaseHelper.getCrimesList(adapterCrimes, requireContext(), crimesList, "1")
        } else {
            fireBaseHelper.getCrimesList(adapterCrimes, requireContext(), crimesList, numberSeason)
        }
    }


    override fun onItemClick(position: Int) {
        val crime = crimesList[position]

        // Закрыл доступ к элементам под замком
        if (!crime.openCrime) {
            paymentDialog(position)
        } else {
            when (position in 0..9) {
                (position == 0) -> replaceFragment(DetailsCrimeFragmentTile.newInstance(numberSeason,"1"))
                (position == 1) -> replaceFragment(DetailsCrimeFragmentTile.newInstance(numberSeason,"2"))
                (position == 2) -> replaceFragment(DetailsCrimeFragmentTile.newInstance(numberSeason,"3"))
                (position == 3) -> replaceFragment(DetailsCrimeFragmentTile.newInstance(numberSeason,"4"))
                (position == 4) -> replaceFragment(DetailsCrimeFragmentTile.newInstance(numberSeason,"5"))
                (position == 5) -> replaceFragment(DetailsCrimeFragmentTile.newInstance(numberSeason,"6"))
                (position == 6) -> replaceFragment(DetailsCrimeFragmentTile.newInstance(numberSeason,"7"))
                (position == 7) -> replaceFragment(DetailsCrimeFragmentTile.newInstance(numberSeason,"8"))
                (position == 8) -> replaceFragment(DetailsCrimeFragmentTile.newInstance(numberSeason,"9"))
                (position == 9) -> replaceFragment(DetailsCrimeFragmentTile.newInstance(numberSeason,"10"))
                else -> {}
            }
        }
    }

     private fun initCarouselViewCrimes() {
        crimesList.removeAll(crimesList.toSet())
        binding.apply {
            carouselRecyclerView.adapter = adapterCrimes
            carouselRecyclerView.set3DItem(false)
            carouselRecyclerView.setAlpha(true)
            carouselRecyclerView.setInfinite(true)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_for_fragment, fragment)
            .addToBackStack("")
            .commit()
    }

    private fun paymentDialog(position: Int) {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_payment, null)
        val myDialog = Dialog(requireContext())
        val inviteCode = dialogBinding.findViewById<EditText>(R.id.invite_code)

        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        fun unlockCrime() {
            crimesList[position].openCrime = true
            Toast.makeText(
                requireContext(),
                "Успешно",
                Toast.LENGTH_LONG
            ).show()
            myDialog.dismiss()
            replaceFragment(DetailsCrimeFragmentList.newInstance(numberSeason,crimesList[position+1].toString()))
        }

        // Обработка кнопки подтвердить инвайт код (Проверка кодов для каждого дела)
        dialogBinding.findViewById<AppCompatButton>(R.id.confirm_invite_code).setOnClickListener {
            if (position == 2 && inviteCode.text.toString() == "QWERTY") {
                unlockCrime()
            } else if (position == 3 && inviteCode.text.toString() == "QWERTY2") {
                unlockCrime()
            } else if (position == 4 && inviteCode.text.toString() == "QWERTY3") {
                unlockCrime()
            } else if (position == 5 && inviteCode.text.toString() == "QWERTY4") {
                unlockCrime()
            } else if (position == 6 && inviteCode.text.toString() == "QWERTY5") {
                unlockCrime()
            } else if (position == 7 && inviteCode.text.toString() == "QWERTY6") {
                unlockCrime()
            } else if (position == 8 && inviteCode.text.toString() == "QWERTY7") {
                unlockCrime()
            } else if (position == 9 && inviteCode.text.toString() == "QWERTY8") {
                unlockCrime()
            } else {
                crimesList[position].openCrime = false
                Toast.makeText(
                    requireContext(),
                    "Неверный код",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        // Обработка кнопки Да (Подтверждение оплаты)
        dialogBinding.findViewById<Button>(R.id.alert_yes_payment_dialog).setOnClickListener {
            myDialog.dismiss()
            Toast.makeText(
                requireContext(),
                "Переход на сторонний сервис оплаты",
                Toast.LENGTH_LONG
            ).show()
        }
        // Обработка кнопки Отмена (Закрытие диалогового окна)
        dialogBinding.findViewById<Button>(R.id.alert_no_payment_dialog).setOnClickListener {
            myDialog.dismiss()
        }
    }


    private fun callFullScreen() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // для ранних версий API
            requireActivity().window.decorView.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            // для более поздних версий API
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

    override fun onStart() {
        super.onStart()
        callFullScreen()
    }

    companion object {

        fun newInstance(numberSeason: String): CrimesFragment {
            val bundle = Bundle()
            bundle.putString("KEY_SEASON", numberSeason)
            val fragment = CrimesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}