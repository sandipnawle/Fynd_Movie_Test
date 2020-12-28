package com.sandip.fynd_movie_test.model.fynd_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sandip.fynd_movie_test.model.data.MovieParent

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieParent: MovieParent): Long

    @Query("Select * from movieParent order by page asc")
    fun getAllMovies() : List<MovieParent?>?

    @Query("Delete from movieParent")
    fun clearMovies()
}