package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentDetailsCrimeTileBinding
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.view.adapters.DetailsAdapterTile
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DetailsCrimeFragmentTile : Fragment(),OnItemClick {

    private val listDetails: ArrayList<String> = ArrayList()

    private var _binding: FragmentDetailsCrimeTileBinding? = null
    private val binding: FragmentDetailsCrimeTileBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsCrimeTileBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        binding.apply {
            changeLayouts.setOnClickListener { replaceFragment(DetailsCrimeFragmentList()) }
            detailsBack.setOnClickListener {
               requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun initRecyclerView() {
        val arrayNameDetails = resources.getStringArray(R.array.details_crime_list)
        for (i in arrayNameDetails) {
            listDetails.add(i)
        }
        val recyclerView: RecyclerView = binding.recyclerViewDetails
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        recyclerView.adapter = DetailsAdapterTile(listDetails, requireContext(), this)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .add(R.id.container_for_fragment, fragment)
            .addToBackStack("")
            .hide(this)
            .commit()
    }

    override fun onItemClick(position: Int) {
        val description = listDetails[position]
        replaceFragment(DescriptionsDetailsFragment.newInstance(description))
    }
}