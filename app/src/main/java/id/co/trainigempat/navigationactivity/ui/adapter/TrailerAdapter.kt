package co.id.movielist.adapter

import android.content.Intent
import android.net.Uri
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.trainigempat.navigationactivity.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.co.trainigempat.movielist.model.Trailer
import kotlinx.android.synthetic.main.list_trailer_movie.view.*

class TrailerAdapter (
    private val trailerList: List<Trailer>
): RecyclerView.Adapter<TrailerAdapter.TrailersViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrailersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_trailer_movie, parent, false
        )
        return TrailersViewHolder(view)
    }

    override fun getItemCount(): Int = trailerList.size

    override fun onBindViewHolder(holder: TrailersViewHolder, position: Int) {
        val trailers = trailerList[position]
//        holder.itemView.apply {
//          imagePoster.setBackgroundResource(playing.image)
//            textTitle.text = playing.title
//        }
        holder.bindView(trailers)
    }

    inner class TrailersViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(trailers: Trailer) {
            itemView.apply {
                Glide
                    .with(context).asDrawable()
                    .load("https://i1.ytimg.com/vi/" + trailers.key + "/0.jpg")
//                  .load(POSTER_URL + movie.posterPath)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageTrailer)

                setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse( "https://www.youtube.com/watch?v=" + trailers.key))
                    context.startActivity(intent)

                }
            }
        }
    }


}