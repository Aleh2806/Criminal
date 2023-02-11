package aleh.ahiyevich.criminal.view.adapters

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.model.OnItemClick
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_details.view.*

class DetailsAdapter(
    private val listDetails: ArrayList<String>,
    private val context: Context,
    private val listener: OnItemClick
) : RecyclerView.Adapter<DetailsAdapter.ItemDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDetailsViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_details, parent, false)
        return ItemDetailsViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ItemDetailsViewHolder, position: Int) {
        val nameDetails = listDetails[position]

        holder.nameDetails.text = nameDetails
    }

    override fun getItemCount(): Int {
        return listDetails.size
    }

    inner class ItemDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val nameDetails: TextView = itemView.name_details

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