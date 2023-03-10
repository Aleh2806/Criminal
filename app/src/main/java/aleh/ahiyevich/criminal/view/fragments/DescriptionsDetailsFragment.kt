package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.databinding.FragmentDescriptionsDetailsBinding
import aleh.ahiyevich.criminal.model.Materials
import aleh.ahiyevich.criminal.repository.FireBaseHelper
import aleh.ahiyevich.criminal.view.adapters.DescriptionsDetailsAdapter
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager


class DescriptionsDetailsFragment : Fragment() {

    private val materialsList: ArrayList<Materials> = ArrayList()
    private val fireBaseHelper = FireBaseHelper()
    val adapter = DescriptionsDetailsAdapter(materialsList)

    private var _binding: FragmentDescriptionsDetailsBinding? = null
    private val binding: FragmentDescriptionsDetailsBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numberSeason = arguments?.getString("KEY_SEASON")
        val numberCrime = arguments?.getString("KEY_CRIME")
        val materialName = arguments?.getString("KEY_MATERIAL")
        val tittleDescriptions = arguments?.getString("KEY_TITTLE")
        binding.descriptionDetails.text = tittleDescriptions

        fireBaseHelper.getMaterials(
            adapter,
            requireContext(),
            materialsList,
            numberSeason!!,
            numberCrime!!,
            materialName!!
        )

        initRecyclerView()

        binding.btnBack.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .popBackStack()
        }
    }

    private fun initRecyclerView() {
        val recyclerView = binding.recyclerViewDescriptions
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
        }
    }

    companion object {
        fun newInstance(
            numberSeason: String,
            numberCrime: String,
            descriptionTitle: String,
            materialName: String
        ): DescriptionsDetailsFragment {
            val bundle = Bundle()
            bundle.putString("KEY_TITTLE", descriptionTitle)
            bundle.putString("KEY_SEASON", numberSeason)
            bundle.putString("KEY_CRIME", numberCrime)
            bundle.putString("KEY_MATERIAL", materialName)
            val fragment = DescriptionsDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}



