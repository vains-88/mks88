package id.co.trainigempat.movielist.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.co.trainigempat.movielist.data.local.MovieDb
import id.co.trainigempat.movielist.data.remote.MovieApiEndpoint
import id.co.trainigempat.movielist.model.Movie
import id.co.trainigempat.movielist.model.MovieRespon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository(
    private val remote: MovieApiEndpoint,
    private val local : MovieDb
) {
    private val moviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getMovies(type: String) : LiveData<List<Movie>> {
        if(type == "popular") {
            fetchPopularMovies()
        } else if (type == "favorites"){
            getFavoriteMovies()
        }

        return moviesLiveData
    }

    private fun getFavoriteMovies() {
        val favoriteMovies = local.movieDao().getMovies()

        Log.i("movies", favoriteMovies.size.toString())

        if(favoriteMovies.isNotEmpty()) {
            moviesLiveData.postValue(favoriteMovies)
        } else {
            moviesLiveData.postValue(listOf())
        }
    }

    private fun fetchPopularMovies() {
        isLoading.value = true
        remote.getPopularMovies("678ef42a1b584848591cbd02ac3899c3")
            .enqueue(object : Callback<MovieRespon> {
                override fun onFailure(call: Call<MovieRespon>, t: Throwable) {
                    Log.e("Error Message", t.localizedMessage!!)
                    isLoading.value = false
                }

                override fun onResponse(
                    call: Call<MovieRespon>, response: Response<MovieRespon>
                ) {
                    if (response.isSuccessful) {
                        val movieResponse = response.body()
                        moviesLiveData.postValue(movieResponse?.results)
                    } else {
                        Log.e("movies", "get response failed")
                    }
                    try {
                        isLoading.value = false
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                        Log.e("state", e.localizedMessage!!)
                    }

                }
            })
    }
}