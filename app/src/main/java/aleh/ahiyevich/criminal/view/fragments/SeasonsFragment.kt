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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
                    Toast.makeText(requireContext(), "???? ???? ?????????????? ????????????????", Toast.LENGTH_LONG)
                        .show()
                R.id.profile_bottom_menu ->
                    replaceFragment(ProfileFragment())
            }
            true
        }
    }

    override fun onItemClick(position: Int) {
        // TODO: ???????? ?????????? ?????????????? ?????????????? ???? ?????????????????? ???????????? (???????????? ?????? ?? ????????) ????
        //  ???????? ???????????? (???? ???????????? ???????? ???????????????? ?????????? ???? ?????????????? ???? ?????????????????? ???????? )
        val season = seasonsList[position]

        // ???????????? ???????????? ?? ?????????????????? ?????? ????????????
        if (!season.openSeason) {
            Toast.makeText(requireContext(), "?????????? ????????????", Toast.LENGTH_SHORT).show()
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
            .replace(R.id.container_for_fragment, fragment)
            .commit()
    }

    private fun callFullScreen() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // ?????? ???????????? ???????????? API
            requireActivity().window.decorView.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            // ?????? ?????????? ?????????????? ???????????? API
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

    override fun onStart() {
        super.onStart()
        callFullScreen()
    }

}
