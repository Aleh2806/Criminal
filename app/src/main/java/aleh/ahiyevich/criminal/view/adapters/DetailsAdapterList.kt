package aleh.ahiyevich.criminal.view.adapters

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.model.OnItemClick
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_details_list.view.*

class DetailsAdapterList(
    private val listDetails: ArrayList<String>,
    private val listener: OnItemClick
) : RecyclerView.Adapter<DetailsAdapterList.ItemDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDetailsViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_details_list, parent, false)
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
    val nameDetails: TextView = itemView.name_details_list

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