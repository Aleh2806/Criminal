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
import android.widget.Toast
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
            paymentDialog()
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

    private fun paymentDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_payment, null)
        val myDialog = Dialog(requireContext())

        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        dialogBinding.findViewById<Button>(R.id.alert_yes_payment_dialog).setOnClickListener {
            myDialog.dismiss()
            Toast.makeText(
                requireContext(),
                "Переход на сторонний сервис оплаты",
                Toast.LENGTH_LONG
            ).show()
        }

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