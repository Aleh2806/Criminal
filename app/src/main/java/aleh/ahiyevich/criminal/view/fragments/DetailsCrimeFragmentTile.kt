package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentDetailsCrimeTileBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_details_crime_list.*
import kotlinx.android.synthetic.main.fragment_details_crime_tile.*


class DetailsCrimeFragmentTile : Fragment() {


    private var _binding: FragmentDetailsCrimeTileBinding? = null
    private val binding: FragmentDetailsCrimeTileBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsCrimeTileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailsBack.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .popBackStack()
        }

        if (details_tile != details_list){
            binding.changerLayouts.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container_for_fragment,DetailsCrimeFragmentList())
                    .hide(this)
                    .commit()
            }
        }
    }

}