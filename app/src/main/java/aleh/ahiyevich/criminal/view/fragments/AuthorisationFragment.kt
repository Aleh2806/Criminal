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
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.dialog_registration.view.*
import kotlinx.android.synthetic.main.fragment_authorisation.*


class AuthorisationFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseDatabase
    lateinit var users: DatabaseReference


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

        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        val email = dialogBinding.registration_edit_email
        val password = dialogBinding.registration_edit_password

        dialogBinding.registration_yes.setOnClickListener {
            // Делаю проверку на пустые поля
            if (TextUtils.isEmpty(email.text.toString())) {
                Toast.makeText(requireContext(), "Введите ваш e-mail", Toast.LENGTH_SHORT)
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
                    users
                        // Придумать что можно использовать,как альтернативу ключа для
                        // авторизации, то что после оператора Элвиса
                        .child(FirebaseAuth.getInstance().currentUser?.uid ?: user.email)
                        .setValue(user)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(),"Регистрация прошла успешо!",Toast.LENGTH_SHORT).show()
                        }
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