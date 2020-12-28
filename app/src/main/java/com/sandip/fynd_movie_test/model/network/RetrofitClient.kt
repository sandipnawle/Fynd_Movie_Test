package com.sandip.fynd_movie_test.model.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        @Volatile
        private  var retrofit: Retrofit? = null
        fun getRetrofit():Retrofit = retrofit?: synchronized(this) {
            retrofit?:buildRetrofit().also { retrofit = it }
        }

        private fun buildRetrofit()=
            Retrofit.Builder().baseUrl("https://api.themoviedb.org/").client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create()).build()


        private fun getOkHttpClient(): OkHttpClient {
            val headerInterceptor =
                Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json;charset=utf-8")
                        .build()
                    chain.proceed(request)
                }
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            return OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(interceptor)
                .build()
        }
    }
}