package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentSeassonsBinding
import aleh.ahiyevich.criminal.model.FireBaseHelper
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.model.SeasonsU
import aleh.ahiyevich.criminal.view.adapters.SeasonsAdapter
import aleh.ahiyevich.criminal.view.adapters.TestAdapterForFirebase
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SeasonsFragment : Fragment(), OnItemClick {

    private val fireBaseHelper = FireBaseHelper()
    private val seasonsList: ArrayList<SeasonsU> = ArrayList()
    private val adapter = SeasonsAdapter(seasonsList, this)


    private var _binding: FragmentSeassonsBinding? = null
    private val binding: FragmentSeassonsBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeassonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        fireBaseHelper.getSeasonsList(adapter, requireContext(), seasonsList)
        processingBottomMenu()
    }


    private fun initRecyclerView() {
        val recyclerView: RecyclerView = binding.recyclerViewSeasons
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this.context, 2)
    }

    private fun processingBottomMenu() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_bottom_menu ->
                    Toast.makeText(requireContext(), "Вы на главной странице", Toast.LENGTH_LONG)
                        .show()
                R.id.profile_bottom_menu ->
                    replaceFragment(ProfileFragment())
            }
            true
        }
    }

    override fun onItemClick(position: Int) {
        // TODO: Сюда думаю всунуть запросы на получение данных (списка дел с фото) из
        //  базы данных (На уровне ниже засунуть такие же запросы на материалы дела )

        when (position in 0..9) {
            (position == 0) -> replaceFragment(CrimesFragment.newInstance("1"))
            (position == 1) -> replaceFragment(CrimesFragment.newInstance("2"))
            (position == 2) -> replaceFragment(CrimesFragment.newInstance("3"))
            (position == 3) -> replaceFragment(CrimesFragment.newInstance("4"))
            (position == 4) -> replaceFragment(CrimesFragment.newInstance("5"))
            (position == 5) -> replaceFragment(CrimesFragment.newInstance("6"))
            (position == 6) -> replaceFragment(CrimesFragment.newInstance("7"))
            (position == 7) -> replaceFragment(CrimesFragment.newInstance("8"))
            (position == 8) -> replaceFragment(CrimesFragment.newInstance("9"))
            (position == 9) -> replaceFragment(CrimesFragment.newInstance("10"))


            else -> {}
        }


        // Закрыл доступ к элементам под замком
//        if (!season.openSeason) {
//            Toast.makeText(requireContext(), "Сезон закрыт", Toast.LENGTH_SHORT).show()
//        } else {
//            replaceFragment(CrimesFragment())
//        }
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_for_fragment, fragment)
            .hide(this)
            .addToBackStack("")
            .commit()
    }
}
