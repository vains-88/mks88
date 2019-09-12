package id.co.trainigempat.navigationactivity.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.movielist.adapter.TrailerAdapter
import com.bumptech.glide.Glide
import id.co.trainigempat.movielist.data.local.MovieDao
import id.co.trainigempat.movielist.data.local.MovieDb
import id.co.trainigempat.movielist.data.remote.MovieService.MovieService.makeService
import id.co.trainigempat.movielist.model.Movie
import id.co.trainigempat.movielist.model.Trailer
import id.co.trainigempat.movielist.model.TrailerVideo
import id.co.trainigempat.navigationactivity.R
import id.co.trainigempat.navigationactivity.ui.adapter.MovieRvAdapter.Companion.BACKDROP_URL
import id.co.trainigempat.navigationactivity.ui.adapter.MovieRvAdapter.Companion.POSTER_URL
import kotlinx.android.synthetic.main.activity_detail_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailMovieActivity : AppCompatActivity() {

    private lateinit var trailerAdapter: TrailerAdapter
    //    private lateinit var trailerList: MutableList<Trailer>
    private lateinit var movie: Movie

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

//        val title = intent.getStringExtra("title")
//        val releaseDate = intent.getStringExtra("releaseDate")
//        val popularity = intent.getDoubleExtra("popularity", 0.0)

        detailViewModel = ViewModelProviders.of(
            this, DetailViewModelFactory(
                makeService(), MovieDb.getInstance(this)
            )
        ).get(DetailViewModel::class.java)

        movie = intent.getParcelableExtra("movieIntent")!!
        showDetailMovie(movie)
        supportActionBar?.title = movie.title

        detailViewModel.isMovieFavorite(movie.id)
        showToast()
        showTrailers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        detailViewModel.isFavorited.observe(this, Observer {
            if (it) {
                menu?.findItem(R.id.favoriteButton)?.icon = resources
                    .getDrawable(R.drawable.ic_favorite_black_24dp)
            } else {
                menu?.findItem(R.id.favoriteButton)?.icon = resources
                    .getDrawable(R.drawable.ic_favorite_border_black_24dp)
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favoriteButton -> {
                detailViewModel.onFavoriteButtonClicked(movie)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDetailMovie(movie: Movie) {
        text_title.text = movie.title
        textReleaseDate.text = movie.releaseDate
        textRating.text = "${movie.voteAverage} / 10"
        textSummary.text = movie.overview
        Glide.with(this)
            .asBitmap()
            .load("$POSTER_URL${movie.posterPath}")
            .into(imagePoster)

        Glide.with(this)
            .asBitmap()
            .load("$BACKDROP_URL${movie.backdropPath}")
            .into(imageBackdrop)

        initView()
        detailViewModel.fetchTrailers(movie.id)
    }

    private fun initView() {
//        trailerList = mutableListOf()

        trailerLabel.apply {
            layoutManager = LinearLayoutManager(
                this@DetailMovieActivity,
                LinearLayoutManager.HORIZONTAL, false
            )
            setHasFixedSize(true)

        }
    }

    private fun showToast() {
        detailViewModel.toastMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun showTrailers() {
        detailViewModel.trailers.observe(this, Observer {
            trailerAdapter = TrailerAdapter(it)
            trailerLabel.adapter = trailerAdapter
        })
    }
}
