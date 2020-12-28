package com.sandip.fynd_movie_test.model.repository

import android.content.Context
import com.sandip.fynd_movie_test.model.data.Movie
import com.sandip.fynd_movie_test.model.data.MovieParent
import com.sandip.fynd_movie_test.model.fynd_db.FyndDatabase
import com.sandip.fynd_movie_test.model.network.ApiHelper
import com.sandip.fynd_movie_test.model.network.ApiService

class HomeRepository(val context: Context) {
    suspend fun getMovies(pageNo: Int): MovieParent? {
        if (ApiHelper.hasInternetConnected(context)) {
            val movieParent = ApiHelper.getInstance().getMovies(pageNo)
            movieParent?.let {
                if(it.page == 1){
                    FyndDatabase.getInstance(context).movieDao().clearMovies()
                }
                val rowId = FyndDatabase.getInstance(context).movieDao().insertMovie(it)
            }
            return movieParent
        } else {
            val movieParentList = FyndDatabase.getInstance(context).movieDao().getAllMovies()
            var movie: MovieParent? = null
            movieParentList?.let {
                movie = it[it.size - 1]
                val movieList = ArrayList<Movie>()
                for (movieParent in it) {
                    movieList.addAll(movieParent!!.results)
                }
                movie?.results?.clear()
                movie?.results?.addAll(movieList)


            }
            return movie
        }

    }

    suspend fun searchMovie(searchQuery: String) =
        ApiHelper.getInstance().getSearchResult(searchQuery)

}