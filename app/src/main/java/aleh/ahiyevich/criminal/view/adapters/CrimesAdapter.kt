package aleh.ahiyevich.criminal.view.adapters

import aleh.ahiyevich.criminal.model.Crimes
import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.model.OnItemClick
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CrimesAdapter(
    private val data: List<Crimes>,
    private val listener: OnItemClick
) :
    RecyclerView.Adapter<CrimesAdapter.ItemCrimesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCrimesViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)

        return ItemCrimesViewHolder(inflatedView)
    }


    override fun onBindViewHolder(holder: ItemCrimesViewHolder, position: Int) {
        val crime: Crimes = data[position]

        if (crime.isOpen){
            holder.lockCrime.visibility = View.GONE
        } else {
            holder.lockCrime.visibility = View.VISIBLE
        }
        holder.nameCrime.text = crime.nameCrime
        holder.imageCrime.setImageResource(crime.imageCrime)

    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class ItemCrimesViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        val nameCrime: TextView = view.findViewById(R.id.name_crime)
        val imageCrime: ImageView = view.findViewById(R.id.image_crime)
        val lockCrime: FrameLayout = view.findViewById(R.id.lock_crime)


        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
}