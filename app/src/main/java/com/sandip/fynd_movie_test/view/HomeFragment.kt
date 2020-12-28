package com.sandip.fynd_movie_test.view

import android.content.Context
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sandip.fynd_movie_test.R
import com.sandip.fynd_movie_test.databinding.FragmentHomeBinding
import com.sandip.fynd_movie_test.model.data.Movie
import com.sandip.fynd_movie_test.model.data.MovieParent
import com.sandip.fynd_movie_test.view.adapter.MovieAdapter
import com.sandip.fynd_movie_test.view_model.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var isLoading = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        retainInstance = true
        homeBinding.lifecycleOwner = this
        homeBinding.homeViewModel = homeViewModel
        setupObservers()
        return homeBinding.root
    }

    private fun setupObservers() {
        homeViewModel.getMovies().observe(viewLifecycleOwner, Observer { movieParent ->
            if(movieParent != null) {
                val pageNo = movieParent.page
                isLoading = false
                if (pageNo == 1) {
                    setupAdapter(movieParent)
                } else {
                    if (this::movieAdapter.isInitialized) {
                        loadPaginationData(movieParent)
                    } else {
                        setupAdapter(movieParent)
                    }
                }
            }else{
                Toast.makeText(context,"Something wrong",Toast.LENGTH_SHORT).show()
            }
        })

        homeViewModel.getSearchResults().observe(viewLifecycleOwner, Observer {
            movieAdapter.showNewData(it.results)
        })

        homeViewModel.loadMovie()
        /* homeViewModel.getMovies().observe(viewLifecycleOwner, Observer {
             it?.let {resources ->
                 when(resources.status){
                     Status.SUCCESS->{
                         isLoading = false
                         resources.data?.let {movieParent->
                             var pageNo = movieParent.page
                             if(pageNo==1){
                                 setupAdapter(movieParent)
                             }else{

                             }
                         }
                     }
                     Status.ERROR->{
                         isLoading = false
                     }
                     Status.LOADING->{
                         isLoading = true
                     }
                 }

             }
         })*/
    }

    private fun loadPaginationData(movieParent: MovieParent) {
        movieAdapter.addList(movieParent.results)
    }

    private fun setupAdapter(movieParent: MovieParent) {
        movieAdapter = MovieAdapter(movieParent.results as ArrayList<Movie>)
        gridLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        homeBinding.listMovie.layoutManager = gridLayoutManager
        homeBinding.listMovie.adapter = movieAdapter

        homeBinding.listMovie.addOnScrollListener(onScrollListener)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


    }

    private var onScrollListener: RecyclerView.OnScrollListener = object :
        RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                val visibleItemCount: Int = gridLayoutManager.getChildCount()
                val totalItemCount: Int = gridLayoutManager.getItemCount()
                val firstVisibleItemPosition: Int = gridLayoutManager.findFirstVisibleItemPosition()
                if (!isLoading) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE
                        && (homeViewModel.getMovies().value!!.total_pages > homeViewModel.getMovies().value!!.page)
                    ) {
                        isLoading = true
                        homeViewModel.callPagination()
                    }
                }
            }
        }

    }


    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }

    fun searchQuery(query: String?) {
        if (query != null && query.length > 0) {
            homeViewModel.searchMovie(query)
        } else {
            hideSearch()
        }
    }

    private fun hideSearch() {
        movieAdapter.showNewData(homeViewModel.getMovies().value?.results)
    }

}