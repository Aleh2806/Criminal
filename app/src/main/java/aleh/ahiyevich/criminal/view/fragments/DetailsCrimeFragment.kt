package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.databinding.FragmentDetailsCrimeBinding
import aleh.ahiyevich.criminal.model.Crimes
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_details_crime.*


class DetailsCrimeFragment : Fragment() {

    private var _binding: FragmentDetailsCrimeBinding? = null
    private val binding: FragmentDetailsCrimeBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsCrimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Передача параметров из фрагмента во фрагмент
        // если аргументы не null, получаем Парселизированный обьект положенный
        // в бутылку из метода newInstance и отрисовываем фрагмент
        val crime = arguments?.getParcelable<Crimes>(BUNDLE_CRIME_EXTRA)
        if (crime != null)
            renderData(crime)
    }


    // Отрисовка фрагмента через обьект
    private fun renderData(crime: Crimes) {

        binding.apply {
            nameCrimeDetails.text = crime.nameCrime
            descriptionCrimeDetails.text = crime.descriptionCrime
            imageCrimeDetails.setImageResource(crime.imageCrime)
            details_back.setOnClickListener {
                requireActivity()
                    .supportFragmentManager
                    .popBackStack()
            }
        }
    }

    // Передача параметров из фрагмента во фрагмент, кладем в бутылку
    // Парселизированный обьект

    // Затем в аргументы этого Фрагмента ложим бутылку, в которую только что положили
    // Парселизированный обьект
    companion object {
        const val BUNDLE_CRIME_EXTRA = "Crime"

        fun newInstance(crime: Crimes): DetailsCrimeFragment {
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_CRIME_EXTRA, crime)
            val fragment = DetailsCrimeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}

