package id.co.trainigempat.navigationactivity.ui.Populer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.co.trainigempat.movielist.data.repository.MovieRepository
import id.co.trainigempat.movielist.model.Movie


class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getPopularMovies(): LiveData<List<Movie>> =
        repository.getMovies("popular")


}

