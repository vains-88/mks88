package id.co.trainigempat.movielist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.co.trainigempat.movielist.model.Movie
import id.co.trainigempat.movielist.model.MovieRespon


@Dao
interface MovieDao {

    @Insert
    fun insertMovie(movie: Movie)

    @Query("Select * From movies")
    fun getMovies() : List<Movie>

    @Query("Select id From movies where id = :id")
        fun findMovieById(id: Int):Int

    @Query("Delete From movies where id = :id")
    fun deleteMovieById(id: Int)

}