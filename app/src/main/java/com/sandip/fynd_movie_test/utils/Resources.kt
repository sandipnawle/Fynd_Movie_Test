package com.sandip.fynd_movie_test.utils

data class Resources<out T>(val status : Status, val data:T?, val message :String?){
    companion object{
        fun <T> success(data : T):Resources<T> = Resources(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data : T?, message: String) = Resources(Status.ERROR,data,message)

        fun <T> loading (data : T?) = Resources(Status.LOADING,data, null)
    }
}