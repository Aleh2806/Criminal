package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.databinding.FragmentQuestionsDetailsBinding
import aleh.ahiyevich.criminal.model.ImageU
import aleh.ahiyevich.criminal.view.adapters.QuestionsAdapter
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class QuestionsDetailsFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var firebaseDatabase: FirebaseDatabase


    private var _binding: FragmentQuestionsDetailsBinding? = null
    private val binding: FragmentQuestionsDetailsBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerViewQuestions
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }

        val imagesList: ArrayList<ImageU> = ArrayList()
        var adapter = QuestionsAdapter(imagesList,requireContext())
        firebaseDatabase = FirebaseDatabase.getInstance()


        firebaseDatabase.reference.child("images").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children){
                    var data: ImageU? = dataSnapshot.getValue(ImageU::class.java)
                    imagesList.add(data!!)
                }
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"Fail",Toast.LENGTH_SHORT).show()
            }

        })



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
}