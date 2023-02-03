package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.databinding.FragmentPhotoDetailsBinding
import aleh.ahiyevich.criminal.model.Crimes
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class PhotoDetailsFragment : Fragment() {


    private var _binding: FragmentPhotoDetailsBinding? = null
    private val binding: FragmentPhotoDetailsBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .popBackStack()
        }

        // Передача параметров из фрагмента во фрагмент
        // если аргументы не null, получаем Парселизированный обьект положенный
        // в бутылку из метода newInstance и отрисовываем фрагмент
        val crime = arguments?.getParcelable<Crimes>(BUNDLE_CRIME_EXTRA)

//        if (crime != null) {
//            renderData(crime)
//        }
    }


    // Отрисовка фрагмента через обьект
//    private fun renderData(crime: Crimes) {
//
//        binding.photo1.setImageResource(crime.imageCrime)
//        binding.photo2.setImageResource(crime.imageCrime)
//        binding.photo3.setImageResource(crime.imageCrime)
//        binding.photo4.setImageResource(crime.imageCrime)
//
//    }


    // Передача параметров из фрагмента во фрагмент, кладем в бутылку
    // Парселизированный обьект
    // Затем в аргументы этого Фрагмента ложим бутылку, в которую только что положили
    // Парселизированный обьект
    companion object {
        const val BUNDLE_CRIME_EXTRA = "Crime"

        fun newInstance(crime: Crimes): PhotoDetailsFragment {
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_CRIME_EXTRA, crime)
            val fragment = PhotoDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}



