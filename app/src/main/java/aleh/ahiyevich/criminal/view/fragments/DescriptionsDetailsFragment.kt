package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.databinding.FragmentDescriptionsDetailsBinding
import aleh.ahiyevich.criminal.model.CrimesU
import aleh.ahiyevich.criminal.model.FireBaseHelper
import aleh.ahiyevich.criminal.view.adapters.TestAdapterForFirebase
import android.app.ProgressDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DescriptionsDetailsFragment : Fragment() {

    private val crimesList: ArrayList<CrimesU> = ArrayList()
    private val fireBaseHelper = FireBaseHelper()
    val adapter = TestAdapterForFirebase(crimesList)



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

        initRecyclerView(adapter)

        fireBaseHelper.getCrimesList(adapter,requireContext(),crimesList,"1")

        binding.btnBack.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .popBackStack()
        }

        val tittleDescriptions = arguments?.getString("DESCRIPTION")
        binding.descriptionDetails.text = tittleDescriptions
    }

    private fun initRecyclerView(adapter: TestAdapterForFirebase){
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



