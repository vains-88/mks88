package id.co.trainigempat.movielist.data.remote

import id.co.trainigempat.movielist.model.MovieRespon
import id.co.trainigempat.movielist.model.TrailerVideo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApiEndpoint {
    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String
    ): Call<MovieRespon>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String

    ): Call<MovieRespon>

    @GET("movie/{movie_id}/videos")
    fun getTrailerByMovieId(
    @Path("movie_id") movieId: Int,
    @Query("api_key") apiKey: String
    ): Call<TrailerVideo>

    }