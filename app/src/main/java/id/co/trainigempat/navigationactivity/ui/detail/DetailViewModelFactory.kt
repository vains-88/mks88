package id.co.trainigempat.navigationactivity.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.co.trainigempat.movielist.data.remote.MovieApiEndpoint
import id.co.trainigempat.movielist.data.local.MovieDb

class DetailViewModelFactory(
    private val service: MovieApiEndpoint,
    private val database: MovieDb
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(service, database) as T
    }
}