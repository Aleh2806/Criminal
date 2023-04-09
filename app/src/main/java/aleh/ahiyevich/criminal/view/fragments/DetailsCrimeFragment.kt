package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentDetailsCrimeBinding
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.view.adapters.DetailsAdapter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DetailsCrimeFragment : Fragment(), OnItemClick {

    private val listDetails: ArrayList<String> = ArrayList()

    private var _binding: FragmentDetailsCrimeBinding? = null
    private val binding: FragmentDetailsCrimeBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsCrimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numberSeason = arguments?.getString("KEY_SEASON").toString()

        initRecyclerView()

        binding.apply {
            detailsBack.setOnClickListener {replaceFragment(SeasonsFragment.newInstance(numberSeason))}
        }
    }

    private fun initRecyclerView() {
        val arrayNameDetails = resources.getStringArray(R.array.details_crime_list)
        listDetails.removeAll(listDetails.toSet())

        for (i in arrayNameDetails) {
            listDetails.add(i)
        }
        val recyclerView: RecyclerView = binding.recyclerViewDetails
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = DetailsAdapter(listDetails, this)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
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

    override fun onItemClick(position: Int) {
        val description = listDetails[position]
        val numberSeason = arguments?.getString("KEY_SEASON").toString()
        val numberCrime = arguments?.getString("KEY_CRIME").toString()

        when(position in 0..5){
            (position == 0) -> replaceFragment(DescriptionsDetailsFragment.newInstance(numberSeason,numberCrime,description,"photo"))
            (position == 1) -> replaceFragment(DescriptionsDetailsFragment.newInstance(numberSeason,numberCrime,description,"video"))
            (position == 2) -> replaceFragment(DescriptionsDetailsFragment.newInstance(numberSeason,numberCrime,description,"questions"))
            (position == 3) -> replaceFragment(DescriptionsDetailsFragment.newInstance(numberSeason,numberCrime,description,"suspects"))
            (position == 4) -> replaceFragment(DescriptionsDetailsFragment.newInstance(numberSeason,numberCrime,description,"experts"))
            (position == 5) -> replaceFragment(DescriptionsDetailsFragment.newInstance(numberSeason,numberCrime,description,"deponents"))

            else -> {}
        }
    }

    companion object {
        fun newInstance(numberSeason: String, numberCrime: String): DetailsCrimeFragment {
            val bundle = Bundle()
            bundle.putString("KEY_SEASON", numberSeason)
            bundle.putString("KEY_CRIME", numberCrime)
            val fragment = DetailsCrimeFragment()
            fragment.arguments = bundle
            return fragment
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
}