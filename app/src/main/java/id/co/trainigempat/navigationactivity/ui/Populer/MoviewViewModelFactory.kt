package id.co.trainigempat.navigationactivity.ui.Populer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.co.trainigempat.movielist.data.repository.MovieRepository


class MoviewViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(repository) as T
    }
}