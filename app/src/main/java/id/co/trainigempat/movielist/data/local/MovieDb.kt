package id.co.trainigempat.movielist.data.local

import androidx.fragment.app.FragmentActivity
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.co.trainigempat.movielist.model.Movie


@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private var Instance: MovieDb? = null
        private val lock = Any()

        fun getInstance(context: FragmentActivity): MovieDb {

            if (Instance == null) {
                synchronized(lock) {
                    Instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDb::class.java, "favorite_movies.db"
                    ).allowMainThreadQueries()
                    .build()

                    return Instance as MovieDb
                }
            }
            return Instance as MovieDb
        }

    }


}