package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentSeassonsBinding
import aleh.ahiyevich.criminal.model.ImageU
import aleh.ahiyevich.criminal.view.adapters.TestAdapterForFirebase
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SeasonsFragment : Fragment()/* OnItemClick*/ {

//    private val data = createData()
    lateinit var recyclerView: RecyclerView
    lateinit var firebaseDatabase: FirebaseDatabase

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

//        val recyclerView: RecyclerView = binding.recyclerViewSeasons
//        recyclerView.setHasFixedSize(true)
//        val adapter = SeasonsAdapter(data, this)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

createData()


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

//    override fun onItemClick(position: Int) {
//        val season = data[position]
//
//        // Закрыл доступ к элементам под замком
//        if (!season.isOpenSeason) {
//            Toast.makeText(requireContext(), "Сезон закрыт", Toast.LENGTH_SHORT).show()
//        } else {
//            replaceFragment(CrimesFragment())
//        }
//    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_for_fragment, fragment)
            .hide(this)
            .addToBackStack("")
            .commit()
    }



    private fun createData(): ArrayList<ImageU> {
        val imagesList: ArrayList<ImageU> = ArrayList()

//        recyclerView = binding.recyclerViewSeasons
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            recyclerView.layoutManager = LinearLayoutManager(
//                requireContext(),
//                LinearLayoutManager.HORIZONTAL, false
//            )
//        }
//        val adapter = TestAdapterForFirebase(imagesList, requireContext())
        val recyclerView: RecyclerView = binding.recyclerViewSeasons
        recyclerView.setHasFixedSize(true)
        val adapter = TestAdapterForFirebase(imagesList, requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        firebaseDatabase = FirebaseDatabase.getInstance()


        firebaseDatabase.reference.child("seasons").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val data: ImageU? = dataSnapshot.getValue(ImageU::class.java)
                    imagesList.add(data!!)
                }
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show()
            }

        })
        return imagesList
    }



//    private fun createData(): ArrayList<Seasons> {
//
//        val seasonsData = ArrayList<Seasons>()
//
//        // Получаем данные из импровизированной базы данных
//        val namesSeasons = FakeRepository.nameSeason
//        val imagesSeasons = FakeRepository.imageSeason
//        val isOpenSeasons = FakeRepository.isOpenSeason
//
//        SeasonsId.values().forEach { seasonsId ->
//            if (containsId(seasonsId, namesSeasons, imagesSeasons, isOpenSeasons)) {
//                seasonsData.add(
//                    Seasons(
//                        isOpenSeason = isOpenSeasons[seasonsId]!!,
//                        imageSeason = imagesSeasons[seasonsId]!!,
//                        nameSeason = namesSeasons[seasonsId]!!
//
//                    )
//                )
//            }
//        }
//        return seasonsData
//    }
//
//    private fun containsId(seasonsId: SeasonsId, vararg maps: Map<SeasonsId, Any>): Boolean {
//        maps.forEach {
//            if (seasonsId !in it.keys) {
//                return false
//            }
//        }
//        return true
//    }
}
