package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.databinding.FragmentDescriptionsDetailsBinding
import aleh.ahiyevich.criminal.model.CrimesU
import aleh.ahiyevich.criminal.model.FireBaseHelper
import aleh.ahiyevich.criminal.model.Materials
import aleh.ahiyevich.criminal.view.adapters.TestAdapterForFirebase
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
    val adapter = TestAdapterForFirebase(materialsList)

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


        val tittleDescriptions = arguments?.getString("DESCRIPTION")
        binding.descriptionDetails.text = tittleDescriptions

        initRecyclerView()

//        fireBaseHelper.getMaterialsCrime(adapter, requireContext(), materialsList,"1",)

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
        fun newInstance(descriptionTitle: String): DescriptionsDetailsFragment {
            val bundle = Bundle()
            bundle.putString("DESCRIPTION", descriptionTitle)
            val fragment = DescriptionsDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}



