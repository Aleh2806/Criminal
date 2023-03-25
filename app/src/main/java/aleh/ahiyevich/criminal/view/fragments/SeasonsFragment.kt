package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentSeassonsBinding
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.model.SeasonsU
import aleh.ahiyevich.criminal.repository.FireBaseHelper
import aleh.ahiyevich.criminal.view.adapters.SeasonsAdapter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SeasonsFragment : Fragment(), OnItemClick {

    private val fireBaseHelper = FireBaseHelper()
    private val seasonsList: ArrayList<SeasonsU> = ArrayList()
    private val adapterSeasons = SeasonsAdapter(seasonsList, this)

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

        initRecyclerViewSeasons()
        fireBaseHelper.getSeasonsList(adapterSeasons, requireContext(), seasonsList)
        if (arguments == null){
            replaceFragment(CrimesFragment.newInstance("1"))
        } else {
            val numberSeason = arguments?.getString("KEY_SEASON")
            replaceFragment(CrimesFragment.newInstance(numberSeason.toString()))
        }
    }


    private fun initRecyclerViewSeasons() {
        val recyclerView: RecyclerView = binding.recyclerViewSeasons
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapterSeasons
        recyclerView.layoutManager =
            LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
    }


    override fun onItemClick(position: Int) {
        val season = seasonsList[position]

        // Закрыл доступ к элементам под замком
        if (!season.openSeason) {
            Toast.makeText(requireContext(), "Сезон закрыт", Toast.LENGTH_SHORT).show()
        } else {
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
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_for_crimes_list, fragment)
            .commit()
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

        fun newInstance(numberSeason: String): SeasonsFragment {
            val bundle = Bundle()
            bundle.putString("KEY_SEASON", numberSeason)
            val fragment = SeasonsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}
