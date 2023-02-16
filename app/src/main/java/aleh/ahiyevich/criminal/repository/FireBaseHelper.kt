package aleh.ahiyevich.criminal.repository

import aleh.ahiyevich.criminal.model.CrimesU
import aleh.ahiyevich.criminal.model.Materials
import aleh.ahiyevich.criminal.model.SeasonsU
import aleh.ahiyevich.criminal.view.adapters.CrimesAdapter
import aleh.ahiyevich.criminal.view.adapters.DescriptionsDetailsAdapter
import aleh.ahiyevich.criminal.view.adapters.SeasonsAdapter
import android.content.Context
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
        numberSeason: String
    ) {

        firebaseDatabase.reference.child("seasons").child(numberSeason).child("crimes")
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


    // Получаю список материалов дела(Фото, Видео и т.д)
    fun getMaterials(
        adapter: DescriptionsDetailsAdapter,
        context: Context,
        listCrimes: ArrayList<Materials>,
        numberSeason: String,
        numberCrime: String,
        materialName: String
    ) {
        firebaseDatabase.reference
            .child("seasons")
            .child(numberSeason)
            .child("crimes")
            .child(numberCrime)
            .child(materialName)
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val data = dataSnapshot.getValue(Materials::class.java)
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
}