package com.sandip.fynd_movie_test.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sandip.fynd_movie_test.model.data.Movie

class MovieConverter {
    @TypeConverter
    @Suppress("UNCHECKED_CAST")
    fun fromString(value: String): ArrayList<Movie> {
        val langCodeList = object : TypeToken<ArrayList<Movie>>() {}.type
        return Gson().fromJson<Any>(value, langCodeList) as ArrayList<Movie>
    }

    @TypeConverter
    @Suppress("UNCHECKED_CAST")
    fun fromArrayList(list: ArrayList<Movie>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}