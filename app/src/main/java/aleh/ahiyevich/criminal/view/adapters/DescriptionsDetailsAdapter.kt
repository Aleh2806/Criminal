package aleh.ahiyevich.criminal.view.adapters

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.api.directories.DocumentDescription
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DescriptionsDetailsAdapter(
    private val materialsList: ArrayList<DocumentDescription>
) :
    RecyclerView.Adapter<DescriptionsDetailsAdapter.TestViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_descriptions_details_crime, parent, false)
        return TestViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val currentMaterial = materialsList[position]

        holder.name.text = currentMaterial.name
        holder.description.text = currentMaterial.description

//        Glide.with(holder.itemView.context).load(currentMaterial.material).into(holder.photo)
    }

    override fun getItemCount(): Int {
        return materialsList.size
    }



    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
//        val photo: ImageView = itemView.image
        val name: TextView = itemView.findViewById(R.id.name_descriptions_details_crime)
        val description: TextView = itemView.findViewById(R.id.descriptions_details_crime)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

//            if (position != RecyclerView.NO_POSITION) {
//                listener.onItemClick(position)
//            }
        }


    }
}