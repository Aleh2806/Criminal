package aleh.ahiyevich.criminal.view.adapters

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.model.CrimesU
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.model.SeasonsU
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_descriptions_details_crime.view.*

class TestAdapterForFirebase(
    private val imagesList: ArrayList<CrimesU>
) :
    RecyclerView.Adapter<TestAdapterForFirebase.TestViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_descriptions_details_crime, parent, false)
        return TestViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val currentImage = imagesList[position]

        Glide.with(holder.itemView.context).load(currentImage.imageCrime).into(holder.image)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val image: ImageView = itemView.image

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