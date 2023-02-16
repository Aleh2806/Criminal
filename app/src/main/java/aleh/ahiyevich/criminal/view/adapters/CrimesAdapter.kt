package aleh.ahiyevich.criminal.view.adapters

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.model.CrimesU
import aleh.ahiyevich.criminal.model.OnItemClick
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CrimesAdapter(
    private val data: ArrayList<CrimesU>,
    private val listener: OnItemClick
) : RecyclerView.Adapter<CrimesAdapter.ItemCrimesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCrimesViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)

        return ItemCrimesViewHolder(inflatedView)
    }


    override fun onBindViewHolder(holder: ItemCrimesViewHolder, position: Int) {
        val crime: CrimesU = data[position]

        if (crime.openCrime) {
            holder.lockCrime.visibility = View.GONE
        } else {
            holder.lockCrime.visibility = View.VISIBLE
        }
        holder.nameCrime.text = crime.nameCrime
        Glide.with(holder.itemView.context).load(crime.imageCrime).into(holder.imageCrime)

    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class ItemCrimesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val nameCrime: TextView = itemView.findViewById(R.id.name_crime)
        val imageCrime: ImageView = itemView.findViewById(R.id.image_crime)
        val lockCrime: FrameLayout = itemView.findViewById(R.id.lock_crime)


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
}