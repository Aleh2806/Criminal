package aleh.ahiyevich.criminal.view.adapters

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.model.SeasonsU
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SeasonsAdapter(
    private val data: ArrayList<SeasonsU>,
    private val listener: OnItemClick
) : RecyclerView.Adapter<SeasonsAdapter.ItemSeasonViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSeasonViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tile, parent, false)
        return ItemSeasonViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ItemSeasonViewHolder, position: Int) {
        val season = data[position]

        // Повесил обработку отображения замков на сезонах
        if (season.openSeason) {
            holder.lockSeason.visibility = View.GONE
        } else {
            holder.lockSeason.visibility = View.VISIBLE
        }

        holder.nameSeason.text = season.nameSeason
        Glide.with(holder.itemView.context).load(season.imageSeason).into(holder.image)


    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class ItemSeasonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val nameSeason: TextView = itemView.findViewById(R.id.name_crime)
        val image: ImageView = itemView.findViewById(R.id.image_crime)
        val lockSeason: FrameLayout = itemView.findViewById(R.id.lock_crime)


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