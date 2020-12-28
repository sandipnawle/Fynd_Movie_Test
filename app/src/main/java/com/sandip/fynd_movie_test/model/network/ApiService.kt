package com.sandip.fynd_movie_test.model.network

import com.sandip.fynd_movie_test.model.data.MovieParent
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{moviesPath}")
    suspend fun getMovies(@Path(value = "moviesPath", encoded = true)path: String, @Query("page")pageNo: Int, @Query("api_key")api: String) : MovieParent?


    @GET("{searchResult}")
    suspend fun getSearchResult(@Path(value = "searchResult", encoded = true)path: String, @Query("query")query: String, @Query("api_key")api: String) : MovieParent?

}