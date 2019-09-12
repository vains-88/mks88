package id.co.trainigempat.navigationactivity.ui.Populer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.trainigempat.movielist.data.remote.MovieService.MovieService.makeService
import id.co.trainigempat.movielist.model.Movie
import id.co.trainigempat.movielist.data.repository.MovieRepository
import id.co.trainigempat.navigationactivity.R
import id.co.trainigempat.navigationactivity.ui.adapter.MovieRvAdapter
import kotlinx.android.synthetic.main.fragment_populer.*
import id.co.trainigempat.movielist.data.local.MovieDb

class populerMovieFragment : Fragment() {

    private lateinit var movieRvAdapter: MovieRvAdapter
    private lateinit var movieList: MutableList<Movie>
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_populer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        showLoading()
        showMovies()
    }

    private fun initView() {
        val movieRepository = MovieRepository(makeService(), MovieDb.getInstance(requireActivity()))
        movieViewModel =
            ViewModelProviders.of(requireActivity(), MoviewViewModelFactory(movieRepository))
                .get(MovieViewModel::class.java)
        movieList = mutableListOf()
        movieRvAdapter = MovieRvAdapter(movieList)
        rvMovies.apply {
            //layoutManager = LinearLayoutManager(requireActivity())
            layoutManager = GridLayoutManager(requireActivity(), 2)
            setHasFixedSize(true)
            adapter = movieRvAdapter
        }
    }

    private fun showMovies() {
        movieViewModel.getPopularMovies().observe(
            this, Observer {
                it.map {
                    movieList.add(it)
                    movieRvAdapter.notifyDataSetChanged()
                }
            }
        )
    }

    private fun showLoading() {
        movieViewModel.isLoading.observe(this, Observer {
            if (it)
                loadingBar1.visibility = View.VISIBLE
            else
                loadingBar1.visibility = View.GONE
        })
    }
}










