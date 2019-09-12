package id.co.inaportempat.movielist.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.trainigempat.movielist.data.local.MovieDb
import id.co.trainigempat.movielist.data.repository.MovieRepository
import id.co.trainigempat.movielist.model.Movie

class FavoriteViewModel(private val repository: MovieRepository) : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = repository.isLoading

    fun getFavoriteMovies(): LiveData<List<Movie>> = repository.getMovies("favorites")
}