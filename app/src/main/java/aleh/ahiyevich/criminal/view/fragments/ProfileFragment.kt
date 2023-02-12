package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentProfileBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonBackProfile.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .popBackStack()
        }

        binding.exitToProfile.setOnClickListener {
            auth.signOut()
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_for_fragment, AuthorizationFragment())
                .hide(this)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}