package id.co.trainigempat.navigationactivity.ui.Playing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import id.co.trainigempat.movielist.data.remote.MovieService.MovieService.makeService
import id.co.trainigempat.movielist.model.Movie
import id.co.trainigempat.movielist.model.MovieRespon
import id.co.trainigempat.navigationactivity.R
import id.co.trainigempat.navigationactivity.ui.adapter.RvAdapterHome
import kotlinx.android.synthetic.main.fragment_now_playing_populer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NowPlayingPopuler : Fragment() {

    private lateinit var rvAdapterHome: RvAdapterHome
    private lateinit var movieList: MutableList<Movie>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_now_playing_populer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        movieList = mutableListOf()
        rvAdapterHome = RvAdapterHome(movieList)
        rvMovies2.apply {
            //layoutManager = LinearLayoutManager(this@NowPlayingPopuler, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = GridLayoutManager(requireActivity(), 3)
            setHasFixedSize(true)
            adapter = rvAdapterHome
        }

        fetchNowPlayingMovies()
    }

    private fun fetchNowPlayingMovies() {
        loadingBar2.visibility = View.VISIBLE
        val service = makeService()
        service.getNowPlayingMovies("678ef42a1b584848591cbd02ac3899c3")
            .enqueue(object : Callback<MovieRespon> {
                override fun onFailure(call: Call<MovieRespon>, t: Throwable) {
                    Log.e("Error Message", t.localizedMessage)

                    loadingBar2.visibility = View.GONE
                }

                override fun onResponse(call: Call<MovieRespon>, response: Response<MovieRespon>) {
                    if (response.isSuccessful) {
                        val movieResponse = response.body()
                        movieResponse?.results?.map { movie ->
                            movieList.add(movie)
                            rvAdapterHome.notifyDataSetChanged()
                            Log.d("movies", movie.title)
                        }
                    } else {
                        Log.e("movies", "get response failed")
                    }

                    loadingBar2.visibility = View.GONE
                }

            })
    }


}



