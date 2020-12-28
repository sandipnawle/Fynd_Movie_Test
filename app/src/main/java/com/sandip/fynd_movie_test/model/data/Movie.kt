package com.sandip.fynd_movie_test.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Movie(
    val adult:String="",
    val backdrop_path:String="",
    val id :Int = 0,
    val media_type : String="",
    val original_language : String="",
    val original_title : String = "",
    val overview : String="",
    val poster_path : String="",
    val title: String=""
){
    constructor():this("","",0,"","","","","","")
}