package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentDetailsCrimeTileBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


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

        binding.apply {
//            photo.setOnClickListener { replaceFragment(DescriptionsDetailsFragment()) }
//            video.setOnClickListener { replaceFragment(VideoDetailsFragment()) }
//            questions.setOnClickListener { replaceFragment(QuestionsDetailsFragment()) }
//            suspected.setOnClickListener { replaceFragment(SuspectedDetailsFragment()) }
//            experts.setOnClickListener { replaceFragment(ExpertsDetailsFragment()) }
//            deponents.setOnClickListener { replaceFragment(DeponentsDetailsFragment()) }
            binding.changerLayouts.setOnClickListener { replaceFragment(DetailsCrimeFragmentList()) }
            detailsBack.setOnClickListener {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_for_fragment, CrimesFragment())
                    .commit()
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