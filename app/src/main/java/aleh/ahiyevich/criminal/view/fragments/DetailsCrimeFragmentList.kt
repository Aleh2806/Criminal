package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentDetailsCrimeListBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_details_crime_list.*
import kotlinx.android.synthetic.main.fragment_details_crime_tile.*


class DetailsCrimeFragmentList : Fragment() {

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

        binding.detailsBack.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .popBackStack()
        }

        binding.photo.setOnClickListener {
            replaceFragment(PhotoDetailsFragment())
        }

        if (details_list != details_tile) {
            binding.changerLayouts.setOnClickListener {
                replaceFragment(DetailsCrimeFragmentTile())
            }
        }
    }


    private fun replaceFragment(fragment: Fragment){
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_for_fragment,fragment)
            .hide(this)
            .commit()
    }
}


