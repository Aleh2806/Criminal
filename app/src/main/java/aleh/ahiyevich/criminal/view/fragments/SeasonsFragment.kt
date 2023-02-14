package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentSeassonsBinding
import aleh.ahiyevich.criminal.model.CrimesU
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.model.SeasonsU
import aleh.ahiyevich.criminal.view.adapters.SeasonsAdapter
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

    private lateinit var firebaseDatabase: FirebaseDatabase
    private val seasonsList: ArrayList<SeasonsU> = ArrayList()


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
        val recyclerView: RecyclerView = binding.recyclerViewSeasons
        recyclerView.setHasFixedSize(true)
        val adapter = SeasonsAdapter(seasonsList, this, requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        createData(adapter)

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
        val season = seasonsList[position]
        // TODO: Сюда думаю всунуть запросы на получение данных (списка дел с фото) из
        //  базы данных (На уровне ниже засунуть такие же запросы на материалы дела )
        when (position in 0..9) {
            (position == 0) -> replaceFragment(CrimesFragment())
            (position == 1) -> replaceFragment(SeasonsFragment())
            (position == 2) -> replaceFragment(ProfileFragment())


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


    //Загрузка данных из Firebase
    private fun createData(adapter: SeasonsAdapter) {

        firebaseDatabase = FirebaseDatabase.getInstance()

        firebaseDatabase.reference.child("seasons").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val data: SeasonsU? = dataSnapshot.getValue(SeasonsU::class.java)
                        seasonsList.add(data!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
