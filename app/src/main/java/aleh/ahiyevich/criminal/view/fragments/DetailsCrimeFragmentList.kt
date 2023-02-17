package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentDetailsCrimeListBinding
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.view.adapters.DetailsAdapterList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DetailsCrimeFragmentList : Fragment(), OnItemClick {

    private val listDetails: ArrayList<String> = ArrayList()

    private var _binding: FragmentDetailsCrimeListBinding? = null
    private val binding: FragmentDetailsCrimeListBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsCrimeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        val numberSeason = arguments?.getString("KEY_SEASON").toString()
        val numberCrime = arguments?.getString("KEY_CRIME").toString()
        binding.apply {
            changeLayouts.setOnClickListener { replaceFragment(DetailsCrimeFragmentTile.newInstance(numberSeason,numberCrime)) }
            detailsBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun initRecyclerView() {

        val arrayNameDetails = resources.getStringArray(R.array.details_crime_list)
        // Очищаю список, чтобы RecyclerView не перерисовывал его с дубликатами
        listDetails.removeAll(listDetails.toSet())
        for (i in arrayNameDetails) {
            listDetails.add(i)
        }

        val recyclerView: RecyclerView = binding.recyclerViewDetails
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = DetailsAdapterList(listDetails, this)
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_for_fragment, fragment)
            .addToBackStack("")
            .commit()
    }


    override fun onItemClick(position: Int) {
        val description = listDetails[position]
        val numberSeason = arguments?.getString("KEY_SEASON").toString()
        val numberCrime = arguments?.getString("KEY_CRIME").toString()

        when (position in 0..5) {
            (position == 0) -> replaceFragment(
                DescriptionsDetailsFragment.newInstance(
                    numberSeason,
                    numberCrime,
                    description,
                    "photo"
                )
            )
            (position == 1) -> replaceFragment(
                DescriptionsDetailsFragment.newInstance(
                    numberSeason,
                    numberCrime,
                    description,
                    "video"
                )
            )
            (position == 2) -> replaceFragment(
                DescriptionsDetailsFragment.newInstance(
                    numberSeason,
                    numberCrime,
                    description,
                    "questions"
                )
            )
            (position == 3) -> replaceFragment(
                DescriptionsDetailsFragment.newInstance(
                    numberSeason,
                    numberCrime,
                    description,
                    "suspects"
                )
            )
            (position == 4) -> replaceFragment(
                DescriptionsDetailsFragment.newInstance(
                    numberSeason,
                    numberCrime,
                    description,
                    "experts"
                )
            )
            (position == 5) -> replaceFragment(
                DescriptionsDetailsFragment.newInstance(
                    numberSeason,
                    numberCrime,
                    description,
                    "deponents"
                )
            )

            else -> {}
        }

    }


    companion object {
        fun newInstance(numberSeason: String, numberCrime: String): DetailsCrimeFragmentList {
            val bundle = Bundle()
            bundle.putString("KEY_SEASON", numberSeason)
            bundle.putString("KEY_CRIME", numberCrime)
            val fragment = DetailsCrimeFragmentList()
            fragment.arguments = bundle
            return fragment
        }
    }
}




