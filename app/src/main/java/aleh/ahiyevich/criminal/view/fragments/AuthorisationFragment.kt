package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentAuthorisationBinding
import aleh.ahiyevich.criminal.model.User
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.dialog_registration.view.*


class AuthorisationFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var users: DatabaseReference


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

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        users = db.getReference("Users")

        binding.buttonEntry.setOnClickListener {
            authorisationUser()
        }

        binding.buttonRegistration.setOnClickListener {
            showRegistrationDialogAndRegistrationUser()
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_for_fragment, SeasonsFragment())
                .addToBackStack("")
                .commit()
        }
    }

    private fun authorisationUser() {

        val email = binding.logInEmailPerson
        val password = binding.logInPasswordPerson

        if (TextUtils.isEmpty(email.text.toString())) {
            Toast.makeText(requireContext(), "Введите ваш e-mail", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(password.text.toString())) {
            Toast.makeText(requireContext(), "Введите ваш пароль", Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnSuccessListener {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_for_fragment, SeasonsFragment())
                    .hide(this)
                    .commit()
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Ошибка авторизации",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


    private fun showRegistrationDialogAndRegistrationUser() {

        val dialogBinding = layoutInflater.inflate(R.layout.dialog_registration, null)
        val myDialog = Dialog(requireContext())
        val email = dialogBinding.registration_edit_email
        val password = dialogBinding.registration_edit_password

        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        dialogBinding.registration_yes.setOnClickListener {
            // Делаю проверку на пустые поля
            if (TextUtils.isEmpty(email.text.toString())) {
                Toast.makeText(requireContext(), "Введите ваш e-mail", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password.text.toString())) {
                Toast.makeText(
                    requireContext(),
                    "Введите пароль",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }
            if (password.text.toString().length < 7) {
                Toast.makeText(
                    requireContext(),
                    "Пароль должен быть более 7 символов",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }

            // Регистрация пользователя в БД
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnSuccessListener {
                    val user = User(email.text.toString(), password.text.toString())

                    // Придумать что можно использовать,как альтернативу ключа для
                    // авторизации, то что после оператора Элвиса
                    users
                        .child(FirebaseAuth.getInstance().currentUser?.uid ?: user.email)
                        .setValue(user)
                    Toast.makeText(
                        requireContext(),
                        "Регистрация прошла успешо!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка регистрации",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            myDialog.dismiss()
        }

        dialogBinding.registration_cancel.setOnClickListener {
            myDialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}