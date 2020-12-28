package com.sandip.fynd_movie_test.model.data

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.sandip.fynd_movie_test.model.converter.MovieConverter

@Entity(tableName = "movieParent")
data class MovieParent(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "autoId")
    @SerializedName("autoId")
    var autoId: Long = 0,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("page")
    val page: Int = 0,

    @SerializedName("results")
    @TypeConverters(MovieConverter::class)
    val results: ArrayList<Movie>,

    @SerializedName("total_pages")
    val total_pages: Int = 0,

    @SerializedName("total_results")
    val total_results:Int=0
){
    constructor():this(0,0,"","",0,ArrayList(),0,0)
}