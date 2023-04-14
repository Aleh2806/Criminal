package aleh.ahiyevich.criminal.view.adapters

import aleh.ahiyevich.criminal.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VideoListDetailsAdapter(private val videoList: List<String>) :
    RecyclerView.Adapter<VideoListDetailsAdapter.VideoListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_video_help_list, parent, false)
        return VideoListViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        val name = videoList[position]
        holder.name.text = name
    }

    override fun getItemCount(): Int {
        return videoList.size
    }


    class VideoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name: TextView = itemView.findViewById(R.id.name_details_tile)

    }
}