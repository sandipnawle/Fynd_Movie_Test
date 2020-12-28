package com.sandip.fynd_movie_test.model.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.SyncStateContract
import android.util.Log
import com.sandip.fynd_movie_test.model.data.MovieParent
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ApiHelper{

    companion object{
        val api = RetrofitClient.getRetrofit().create(ApiService::class.java)

        @Volatile
        private var apiHelper : ApiHelper?=null
        fun getInstance() : ApiHelper = apiHelper?: synchronized(this){
            apiHelper?:ApiHelper().also { apiHelper = it }
        }

        private fun hasNetworkAvailable(context: Context): Boolean {
            val service = Context.CONNECTIVITY_SERVICE
            val manager = context.getSystemService(service) as ConnectivityManager?
            val network = manager?.activeNetworkInfo
            return (network != null && (network.isConnected || network.isConnectedOrConnecting))
        }

        fun hasInternetConnected(context: Context): Boolean {
            if (hasNetworkAvailable(context)) {
                try {
                    val connection = URL("https://www.google.com/").openConnection() as HttpURLConnection
                    connection.setRequestProperty("User-Agent", "ConnectionTest")
                    connection.setRequestProperty("Connection", "close")
                    connection.connectTimeout = 1000 // configurable
                    connection.connect()
                    return (connection.responseCode == 200)
                } catch (e: IOException) {

                }
            } else {

            }
            return false
        }
    }

    suspend fun getMovies(pageNo : Int): MovieParent? {
        val path = "4/list/1"
        return api.getMovies(path,pageNo,"8e8a3581341512a8c96ee55103474533")
    }

    suspend fun getSearchResult(query : String): MovieParent? {
        val path = "3/search/movie"
        return api.getSearchResult(path,query,"8e8a3581341512a8c96ee55103474533")
    }



}