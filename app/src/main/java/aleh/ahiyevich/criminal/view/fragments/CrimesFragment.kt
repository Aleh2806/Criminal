package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentCrimesListBinding
import aleh.ahiyevich.criminal.model.CrimeId
import aleh.ahiyevich.criminal.model.Crimes
import aleh.ahiyevich.criminal.model.FakeRepository
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.view.adapters.CrimesAdapter
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CrimesFragment : Fragment(), OnItemClick {

    private val data = createData()

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


        val recyclerView: RecyclerView = binding.recyclerViewCrimes
        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = CrimesAdapter(data, this)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_bottom_menu ->
                    replaceFragment(SeasonsFragment())
                R.id.profile_bottom_menu ->
                    replaceFragment(ProfileFragment())
            }
            true
        }
    }


    override fun onItemClick(position: Int) {
        val crime = data[position]

        // Закрыл доступ к элементам под замком
        if (!crime.isOpen) {
            paymentDialog(position)
        } else {
            replaceFragment(DetailsCrimeFragmentList())
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_for_fragment, fragment)
            .addToBackStack("")
            .hide(this)
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

        // Обработка кнопки подтвердить инвайт код (Проверка кодов для каждого дела)
        dialogBinding.findViewById<AppCompatButton>(R.id.confirm_invite_code).setOnClickListener {
            if (position == 2 && inviteCode.text.toString() == "QWERTY") {
                data[position].isOpen = true
                Toast.makeText(
                    requireContext(),
                    "Успешно",
                    Toast.LENGTH_LONG
                ).show()
                myDialog.dismiss()
                replaceFragment(DetailsCrimeFragmentList())
            } else if (position == 3 && inviteCode.text.toString() == "QWERTY2") {
                data[position].isOpen = true
                Toast.makeText(
                    requireContext(),
                    "Успешно",
                    Toast.LENGTH_LONG
                ).show()
                myDialog.dismiss()
                replaceFragment(DetailsCrimeFragmentList())
            } else if (position == 4 && inviteCode.text.toString() == "QWERTY3") {
                data[position].isOpen = true
                Toast.makeText(
                    requireContext(),
                    "Успешно",
                    Toast.LENGTH_LONG
                ).show()
                myDialog.dismiss()
                replaceFragment(DetailsCrimeFragmentList())
            } else if (position == 5 && inviteCode.text.toString() == "QWERTY4") {
                data[position].isOpen = true
                Toast.makeText(
                    requireContext(),
                    "Успешно",
                    Toast.LENGTH_LONG
                ).show()
                myDialog.dismiss()
                replaceFragment(DetailsCrimeFragmentList())
            } else if (position == 6 && inviteCode.text.toString() == "QWERTY5") {
                data[position].isOpen = true
                Toast.makeText(
                    requireContext(),
                    "Успешно",
                    Toast.LENGTH_LONG
                ).show()
                myDialog.dismiss()
                replaceFragment(DetailsCrimeFragmentList())
            } else if (position == 7 && inviteCode.text.toString() == "QWERTY6") {
                data[position].isOpen = true
                Toast.makeText(
                    requireContext(),
                    "Успешно",
                    Toast.LENGTH_LONG
                ).show()
                myDialog.dismiss()
                replaceFragment(DetailsCrimeFragmentList())
            } else if (position == 8 && inviteCode.text.toString() == "QWERTY7") {
                data[position].isOpen = true
                Toast.makeText(
                    requireContext(),
                    "Успешно",
                    Toast.LENGTH_LONG
                ).show()
                myDialog.dismiss()
                replaceFragment(DetailsCrimeFragmentList())
            } else if (position == 9 && inviteCode.text.toString() == "QWERTY8") {
                data[position].isOpen = true
                Toast.makeText(
                    requireContext(),
                    "Успешно",
                    Toast.LENGTH_LONG
                ).show()
                myDialog.dismiss()
                replaceFragment(DetailsCrimeFragmentList())
            } else {
                data[position].isOpen = false
                Toast.makeText(
                    requireContext(),
                    "Неверный Инвайт код",
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


    private fun createData(): ArrayList<Crimes> {
        // Получаем данные из импровизированной базы данных
        val namesCrimes = FakeRepository.nameCrime
        val descriptionsCrimes = FakeRepository.descriptionCrime
        val imagesCrimes = FakeRepository.imageCrime
        val isOpen = FakeRepository.isOpen

        val crimesData = ArrayList<Crimes>()


        CrimeId.values().forEach { crimeId ->
            if (containsId(crimeId, namesCrimes, descriptionsCrimes, imagesCrimes, isOpen)) {
                crimesData.add(
                    Crimes(
                        isOpen = isOpen[crimeId]!!,
                        nameCrime = namesCrimes[crimeId]!!,
                        descriptionCrime = descriptionsCrimes[crimeId]!!,
                        imageCrime = imagesCrimes[crimeId]!!
                    )
                )

            }
        }
        return crimesData
    }


    private fun containsId(crimesId: CrimeId, vararg maps: Map<CrimeId, Any>): Boolean {
        maps.forEach {
            if (crimesId !in it.keys) {
                return false
            }
        }
        return true
    }
}