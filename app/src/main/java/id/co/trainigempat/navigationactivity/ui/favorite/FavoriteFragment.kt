package id.co.trainigempat.navigationactivity.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import id.co.inaportempat.movielist.ui.favorite.FavoriteViewModel
import id.co.trainigempat.navigationactivity.R
import id.co.trainigempat.movielist.data.remote.MovieService.MovieService.makeService
import id.co.trainigempat.navigationactivity.ui.adapter.MovieRvAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*
import id.co.trainigempat.movielist.data.repository.MovieRepository
import id.co.trainigempat.movielist.data.local.MovieDb

class FavoriteFragment : Fragment() {

    private lateinit var movieRvAdapter: MovieRvAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onResume() {
        super.onResume()
        initView()
        showFavorites()
    }

    private fun initView() {
        val movieRepository = MovieRepository(
            makeService(),
            MovieDb.getInstance(requireActivity())
        )
        favoriteViewModel = ViewModelProviders.of(
            requireActivity(), FavoriteViewModelFactory(movieRepository)
        ).get(FavoriteViewModel::class.java)

        favFrag.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            setHasFixedSize(true)

        }
    }


    private fun showFavorites() {
        favoriteViewModel.getFavoriteMovies().observe(this, Observer {
            movieRvAdapter = MovieRvAdapter(it)
            favFrag.adapter = movieRvAdapter

        })
    }

}