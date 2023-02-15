package aleh.ahiyevich.criminal.model

import aleh.ahiyevich.criminal.view.adapters.CrimesAdapter
import aleh.ahiyevich.criminal.view.adapters.SeasonsAdapter
import aleh.ahiyevich.criminal.view.adapters.TestAdapterForFirebase
import android.content.Context
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Подумать, как засунуть сюда запросы в базу данных Firebase
class FireBaseHelper {

    private val firebaseDatabase = FirebaseDatabase.getInstance()


    // Получаю список сезонов
    fun getSeasonsList(
        adapter: SeasonsAdapter,
        context: Context,
        listSeasons: ArrayList<SeasonsU>
    ) {

        firebaseDatabase.reference.child("seasons").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val data: SeasonsU? = dataSnapshot.getValue(SeasonsU::class.java)
                        listSeasons.add(data!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Ошибка загрузки", Toast.LENGTH_SHORT).show()
            }

        })
    }


    // Получаю список дел
    fun getCrimesList(
        adapter: CrimesAdapter,
        context: Context,
        listCrimes: ArrayList<CrimesU>,
        numberCrime: String
    ) {

        firebaseDatabase.reference.child("seasons").child(numberCrime).child("crimes")
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val data = dataSnapshot.getValue(CrimesU::class.java)
                            listCrimes.add(data!!)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Ошибка загрузки", Toast.LENGTH_SHORT).show()
                }

            })
    }


    fun getMaterialsCrime(
        adapter: TestAdapterForFirebase,
        context: Context,
        listMaterials: ArrayList<Materials>,
        numberCrime: String,
        nameMaterials: String
    ) {

        firebaseDatabase.reference.child("seasons").child(numberCrime).child("crimes").child(nameMaterials)
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val data = dataSnapshot.getValue(Materials::class.java)
                            listMaterials.add(data!!)
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