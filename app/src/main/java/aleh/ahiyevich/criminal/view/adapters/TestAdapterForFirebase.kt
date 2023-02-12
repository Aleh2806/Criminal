package aleh.ahiyevich.criminal.view.adapters

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.model.ImageU
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_descriptions_details_crime.view.*

class TestAdapterForFirebase(
    private val imagesList: ArrayList<ImageU>,
    private val context: Context
) :
    RecyclerView.Adapter<TestAdapterForFirebase.TestViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_descriptions_details_crime, parent, false)
        return TestViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val currentImage = imagesList[position]

        Glide.with(context).load(currentImage.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.image
    }
}