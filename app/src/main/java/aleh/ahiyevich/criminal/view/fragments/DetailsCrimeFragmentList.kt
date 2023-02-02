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

        if (details_list != details_tile){
            binding.changerLayouts.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container_for_fragment,DetailsCrimeFragmentTile())
                    .hide(this)
                    .commit()
            }
        }


            // Передача параметров из фрагмента во фрагмент
            // если аргументы не null, получаем Парселизированный обьект положенный
            // в бутылку из метода newInstance и отрисовываем фрагмент
//            val crime = arguments?.getParcelable<Crimes>(BUNDLE_CRIME_EXTRA)
//        if (crime != null)
//            renderData(crime)
//    }
        }

        // Отрисовка фрагмента через обьект
//    private fun renderData(crime: Crimes) {
//
//        binding.apply {
//            nameCrimeDetails.text = crime.nameCrime
//            descriptionCrimeDetails.text = crime.descriptionCrime
//            imageCrimeDetails.setImageResource(crime.imageCrime)
//
//        }
//    }

        // Передача параметров из фрагмента во фрагмент, кладем в бутылку
        // Парселизированный обьект

        // Затем в аргументы этого Фрагмента ложим бутылку, в которую только что положили
        // Парселизированный обьект
//        companion object {
//            const val BUNDLE_CRIME_EXTRA = "Crime"
//
//            fun newInstance(crime: Crimes): DetailsCrimeFragment {
//                val bundle = Bundle()
//                bundle.putParcelable(BUNDLE_CRIME_EXTRA, crime)
//                val fragment = DetailsCrimeFragment()
//                fragment.arguments = bundle
//                return fragment
//            }
//        }
    }


