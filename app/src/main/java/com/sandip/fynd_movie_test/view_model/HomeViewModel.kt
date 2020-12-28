package com.sandip.fynd_movie_test.view_model

import android.app.Application
import androidx.lifecycle.*
import com.sandip.fynd_movie_test.model.data.MovieParent
import com.sandip.fynd_movie_test.model.network.ApiHelper
import com.sandip.fynd_movie_test.model.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val homeRepository : HomeRepository by lazy { HomeRepository(application) }
    private var job : Job? = null
    var currentPageNo = 1
    val mutableMovie = MutableLiveData<MovieParent>()
    val mutableSearch = MutableLiveData<MovieParent>()
//    fun getMovies () = liveData(Dispatchers.IO) {
//        emit(Resources.loading(data = null))
//        try{
//            emit(Resources.success(data = homeRepository.getMovies(currentPageNo)))
//        }catch (e : Exception){
//            emit(Resources.error(data = null,message = e.message ?: "Error occurred"))
//        }
//    }

    fun loadMovie () = GlobalScope.launch(Dispatchers.IO) {
//        emit(Resources.loading(data = null))
        try{
            if(ApiHelper.hasInternetConnected(getApplication()) || currentPageNo == 1) {
                mutableMovie.postValue(homeRepository.getMovies(currentPageNo))
            }else{
                currentPageNo -= 1
            }
        }catch (e : Exception){
            e.printStackTrace()
            currentPageNo -= 1
//            emit(Resources.error(data = null,message = e.message ?: "Error occurred"))
        }
    }

    fun getMovies() = mutableMovie

    fun getSearchResults() = mutableSearch

    fun callPagination() {
        currentPageNo += 1
        loadMovie()
    }

    fun searchMovie(searchQuery: String?) {
        job?.cancel()
        searchQuery?.let {
            job = GlobalScope.launch(Dispatchers.IO) {
                if(ApiHelper.hasInternetConnected(getApplication())) {
                    mutableSearch.postValue(
                        homeRepository.searchMovie(searchQuery) ?: MovieParent()
                    )
                }
            }
        }

    }
}