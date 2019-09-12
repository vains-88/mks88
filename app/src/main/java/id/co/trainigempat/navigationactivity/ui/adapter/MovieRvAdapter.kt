package id.co.trainigempat.navigationactivity.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.co.trainigempat.movielist.model.Movie
import id.co.trainigempat.navigationactivity.R
import id.co.trainigempat.navigationactivity.ui.detail.DetailMovieActivity
import kotlinx.android.synthetic.main.list_movie_item.view.*


class MovieRvAdapter(
    private val movies: List<Movie>
) : RecyclerView.Adapter<MovieRvAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_movie_item, parent, false
        )
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.binVew(movie)
    }

    companion object {
        const val POSTER_URL = "https://image.tmdb.org/t/p/w185/"
        const val BACKDROP_URL = "https://image.tmdb.org/t/p/w500/"
    }

    inner class MovieViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {


        fun binVew(movie: Movie) {
            itemView.apply {
                //                imagePoster.setBackgroundResource(movie.image)
                text_title.text = movie.title
                Glide
                    .with(context).asDrawable()
                    .load(POSTER_URL + movie.posterPath)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imagePoster)

                setOnClickListener {
                    val intent = Intent(context, DetailMovieActivity::class.java)
//                    intent.putExtra("title", movie.title)
//                    intent.putExtra("releaseDate", movie.releaseDate)
//                    intent.putExtra("popularity", movie.popularity)
                    intent.putExtra("movieIntent", movie)
                    context.startActivity(intent)


                }
            }

        }
    }
}








