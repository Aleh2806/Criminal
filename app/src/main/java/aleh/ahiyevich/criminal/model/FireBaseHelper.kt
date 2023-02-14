package aleh.ahiyevich.criminal.model

import aleh.ahiyevich.criminal.view.adapters.TestAdapterForFirebase
import android.content.Context
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Подумать, как засунуть сюда запросы в базу данных Firebase
class FireBaseHelper {


    fun getCrimesList(adapter: TestAdapterForFirebase, context: Context, list:ArrayList<CrimesU>,numberCrime: String) {

        val firebaseDatabase = FirebaseDatabase.getInstance()

        // Получаю список дел
        firebaseDatabase.reference.child("seasons").child(numberCrime).child("crimes")
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val data = dataSnapshot.getValue(CrimesU::class.java)
                            list.add(data!!)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Ошибка загрузки", Toast.LENGTH_SHORT).show()
                }

            })
    }
}