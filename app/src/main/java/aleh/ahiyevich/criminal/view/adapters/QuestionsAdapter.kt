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
import kotlinx.android.synthetic.main.item_details_crime.view.*

class QuestionsAdapter(
    private val imagesList: ArrayList<ImageU>,
    private val context: Context
) :
    RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_details_crime, parent, false)
        return QuestionsViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        val currentImage = imagesList[position]

        Glide.with(context).load(currentImage.imageUrl).into(holder.imageQuestion);
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    class QuestionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageQuestion: ImageView = itemView.image_questions
    }
}