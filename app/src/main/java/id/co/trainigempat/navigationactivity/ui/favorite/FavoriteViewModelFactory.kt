package id.co.trainigempat.navigationactivity.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.co.inaportempat.movielist.ui.favorite.FavoriteViewModel
import id.co.trainigempat.movielist.data.repository.MovieRepository

class FavoriteViewModelFactory(private val database: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(database) as T
    }
}