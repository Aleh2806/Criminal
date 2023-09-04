package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.Constants
import aleh.ahiyevich.criminal.api.directories.DocumentDescription
import aleh.ahiyevich.criminal.databinding.FragmentDescriptionsDetailsBinding
import aleh.ahiyevich.criminal.model.Materials
import aleh.ahiyevich.criminal.repository.DataBaseHelper
import aleh.ahiyevich.criminal.repository.FireBaseHelper
//import aleh.ahiyevich.criminal.repository.FireBaseHelper
import aleh.ahiyevich.criminal.view.adapters.DescriptionsDetailsAdapter
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager


class DescriptionsDetailsFragment : Fragment() {

    private val materialsList: ArrayList<DocumentDescription> = ArrayList()
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
        val sharedPref: SharedPreferences = requireActivity().getPreferences(AppCompatActivity.MODE_PRIVATE)
        val token = sharedPref.getString(Constants.ACCESS_TOKEN,"")

        backFromFragment()
        initRecyclerView()
            DataBaseHelper().getMaterialsCrime(token!!,1, materialsList, adapter)
    }

    private fun backFromFragment(){
        binding.backBtn.setOnClickListener {
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



