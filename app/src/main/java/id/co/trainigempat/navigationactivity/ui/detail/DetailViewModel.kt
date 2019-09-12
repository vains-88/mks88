package id.co.trainigempat.navigationactivity.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.movielist.adapter.TrailerAdapter
import id.co.trainigempat.movielist.data.local.MovieDb
import id.co.trainigempat.movielist.data.remote.MovieApiEndpoint
import id.co.trainigempat.movielist.model.Movie
import id.co.trainigempat.movielist.model.MovieRespon
import id.co.trainigempat.movielist.model.Trailer
import id.co.trainigempat.movielist.model.TrailerVideo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(
    private val service: MovieApiEndpoint,
    private val database: MovieDb
) : ViewModel() {

    val isFavorited = MutableLiveData<Boolean>().apply {
        value = false
    }

    val toastMessage = MutableLiveData<String>()

    val trailers = MutableLiveData<List<Trailer>>().apply {
        listOf<Trailer>()
    }

    fun isMovieFavorite(movieId: Int): LiveData<Boolean> {
        val result = database.movieDao().findMovieById(movieId)

        if (result == movieId) isFavorited.value = true

        return isFavorited
    }

    fun onFavoriteButtonClicked(movie: Movie) {
        if (isFavorited.value == false) {
            addMovieToFavorite(movie)
        } else {
            removeMovieFromFavorite(movie.id)
        }
    }

    private fun addMovieToFavorite(movie: Movie) {
        try {
            database.movieDao().insertMovie(movie)
            toastMessage.value = "Add Movie to Favorite"
            isFavorited.value = true
        } catch (e: Exception) {
            Log.e("insert Error:", e.localizedMessage!!)
        }
    }

    private fun removeMovieFromFavorite(movieId: Int) {
        try {
            database.movieDao().deleteMovieById(movieId)
            toastMessage.value = "Remove Movie from Favorite"
            isFavorited.value = false
        } catch (e: Exception) {
            Log.e("delete Error:", e.localizedMessage!!)
        }
    }

    fun fetchTrailers(movieId: Int) {
        service.getTrailerByMovieId(movieId, "678ef42a1b584848591cbd02ac3899c3").enqueue(object :
            Callback<TrailerVideo> {
            override fun onFailure(call: Call<TrailerVideo>, t: Throwable) {
                Log.i("fetch-trailers-error", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<TrailerVideo>, response: Response<TrailerVideo>
            ) {
                if (response.isSuccessful) {
                    val trailerResponse = response.body()
                    trailers.postValue(trailerResponse?.results)
                }
            }
        })
    }
}