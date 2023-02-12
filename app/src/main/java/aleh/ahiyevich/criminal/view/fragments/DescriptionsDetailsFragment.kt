package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.databinding.FragmentDescriptionsDetailsBinding
import aleh.ahiyevich.criminal.model.ImageU
import aleh.ahiyevich.criminal.view.adapters.TestAdapterForFirebase
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DescriptionsDetailsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var firebaseDatabase: FirebaseDatabase

    private var _binding: FragmentDescriptionsDetailsBinding? = null
    private val binding: FragmentDescriptionsDetailsBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        createData()

        binding.btnBack.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .popBackStack()
        }

        val tittleDescriptions = arguments?.getString("DESCRIPTION")
        binding.descriptionDetails.text = tittleDescriptions

    }

    companion object {
        fun newInstance(descriptionTitle: String): DescriptionsDetailsFragment {
            val bundle = Bundle()
            bundle.putString("DESCRIPTION", descriptionTitle)
            val fragment = DescriptionsDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun createData() {
        val imagesList: ArrayList<ImageU> = ArrayList()

        recyclerView = binding.recyclerViewDescriptions
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
        }
        val adapter = TestAdapterForFirebase(imagesList, requireContext())
        firebaseDatabase = FirebaseDatabase.getInstance()


        firebaseDatabase.reference.child("seasons").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val data: ImageU? = dataSnapshot.getValue(ImageU::class.java)
                    imagesList.add(data!!)
                }
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show()
            }

        })
    }

}



