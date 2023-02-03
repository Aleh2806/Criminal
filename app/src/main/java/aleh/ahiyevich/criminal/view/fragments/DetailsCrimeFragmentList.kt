package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentDetailsCrimeListBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


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

        binding.apply {
            photo.setOnClickListener { replaceFragment(PhotoDetailsFragment()) }
            video.setOnClickListener { replaceFragment(VideoDetailsFragment()) }
            questions.setOnClickListener { replaceFragment(QuestionsDetailsFragment()) }
            suspected.setOnClickListener { replaceFragment(SuspectedDetailsFragment()) }
            experts.setOnClickListener { replaceFragment(ExpertsDetailsFragment()) }
            deponents.setOnClickListener { replaceFragment(DeponentsDetailsFragment()) }
            changerLayouts.setOnClickListener { replaceFragment(DetailsCrimeFragmentTile()) }
            detailsBack.setOnClickListener {
                requireActivity()
                    .supportFragmentManager
                    .popBackStack()
            }
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
}


