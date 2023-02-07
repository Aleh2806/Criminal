package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.databinding.FragmentVideoDetailsBinding
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class VideoDetailsFragment : Fragment() {

    lateinit var storageReference: StorageReference
    lateinit var progressDialog: ProgressDialog

    private var _binding: FragmentVideoDetailsBinding? = null
    private val binding: FragmentVideoDetailsBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        downloadImageFromFirebase()


        binding.btnBack.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun downloadImageFromFirebase() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Fetching image...")
        progressDialog.setCancelable(true)
        progressDialog.show()

        // Загрузка 1 фото из FireStorage
        storageReference = FirebaseStorage.getInstance().getReference("image").child("b.jpg")

        storageReference
        val localeFile = File.createTempFile("tempImage", ".jpg")
        storageReference.getFile(localeFile)
            .addOnSuccessListener {
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }

                val bitmap: Bitmap = BitmapFactory.decodeFile(localeFile.absolutePath)
                binding.videoContent.setImageBitmap(bitmap)
            }
            .addOnFailureListener {
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
                Toast.makeText(requireContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show()
            }
    }
}

