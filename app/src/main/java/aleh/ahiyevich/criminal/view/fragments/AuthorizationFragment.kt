package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.databinding.FragmentAuthorisationBinding
import aleh.ahiyevich.criminal.repository.DataBaseHelper
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthorisationBinding? = null
    private val binding: FragmentAuthorisationBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorisationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonEntry.setOnClickListener {
            val email = binding.logInEmailPerson.text.toString()
            val password = binding.logInPasswordPerson.text.toString()
            DataBaseHelper().login(
                requireContext(),
                email,
                password,
                requireActivity() as AppCompatActivity
            )
        }

    }

    override fun onStart() {
        super.onStart()
        callFullScreen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}