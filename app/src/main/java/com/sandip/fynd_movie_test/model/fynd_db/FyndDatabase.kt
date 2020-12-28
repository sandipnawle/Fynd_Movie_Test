package com.sandip.fynd_movie_test.model.fynd_db

import android.content.Context
import androidx.room.*
import com.sandip.fynd_movie_test.model.converter.MovieConverter
import com.sandip.fynd_movie_test.model.data.Movie
import com.sandip.fynd_movie_test.model.data.MovieParent
import com.sandip.fynd_movie_test.model.fynd_db.dao.MovieDao

@Database(version = 1, exportSchema = false, entities = [MovieParent::class])
@TypeConverters(MovieConverter::class)
abstract class FyndDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    companion object {
        @Volatile
        private var INSTANCE: FyndDatabase? = null

        fun getInstance(context: Context): FyndDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDB(context)
        }

        private fun buildDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FyndDatabase::class.java, "FyndDatabase"
            ).build()
    }
}