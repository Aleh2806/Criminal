package aleh.ahiyevich.criminal.view.adapters

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.model.Seasons
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SeasonsListAdapter(private val data: List<Seasons>, private val listener: OnItemClick) :
    RecyclerView.Adapter<SeasonsListAdapter.ItemSeasonViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSeasonViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemSeasonViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ItemSeasonViewHolder, position: Int) {
        val season = data[position]

        if (season.isOpenSeason){
            holder.lockSeason.visibility = View.GONE
        } else {
            holder.lockSeason.visibility = View.VISIBLE
        }

        holder.nameSeason.text = season.nameSeason
        holder.imageSeason.setImageResource(season.imageSeason)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class ItemSeasonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val nameSeason: TextView = itemView.findViewById(R.id.name_crime)
        val imageSeason: ImageView = itemView.findViewById(R.id.image_crime)
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