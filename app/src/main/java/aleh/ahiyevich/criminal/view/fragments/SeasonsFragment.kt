package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentSeassonsBinding
import aleh.ahiyevich.criminal.model.FakeRepository
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.model.Seasons
import aleh.ahiyevich.criminal.model.SeasonsId
import aleh.ahiyevich.criminal.view.adapters.SeasonsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SeasonsFragment : Fragment(), OnItemClick {

    private val data = createData()

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
        recyclerView.adapter = SeasonsAdapter(data, this)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)


        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_bottom_menu ->
                    Toast.makeText(requireContext(),"Вы на главной странице",Toast.LENGTH_LONG).show()
                R.id.profile_bottom_menu ->
                    replaceFragment(ProfileFragment())
            }
            true
        }
    }




    override fun onItemClick(position: Int) {
        val season = data[position]

        // Закрыл доступ к элементам под замком
        if (!season.isOpenSeason) {
            Toast.makeText(requireContext(), "Сезон закрыт", Toast.LENGTH_SHORT).show()
        } else {
            replaceFragment(CrimesFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_for_fragment, fragment)
            .hide(this)
            .commit()
    }


    private fun createData(): ArrayList<Seasons> {
        // Получаем данные из импровизированной базы данных
        val namesSeasons = FakeRepository.nameSeason
        val imagesSeasons = FakeRepository.imageSeason
        val isOpenSeasons = FakeRepository.isOpenSeason

        val seasonsData = ArrayList<Seasons>()

        SeasonsId.values().forEach { seasonsId ->
            if (containsId(seasonsId, namesSeasons, imagesSeasons, isOpenSeasons)) {
                seasonsData.add(
                    Seasons(
                        isOpenSeason = isOpenSeasons[seasonsId]!!,
                        nameSeason = namesSeasons[seasonsId]!!,
                        imageSeason = imagesSeasons[seasonsId]!!
                    )
                )
            }
        }
        return seasonsData
    }


    private fun containsId(seasonsId: SeasonsId, vararg maps: Map<SeasonsId, Any>): Boolean {
        maps.forEach {
            if (seasonsId !in it.keys) {
                return false
            }
        }
        return true
    }


}