package com.sandip.fynd_movie_test.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sandip.fynd_movie_test.R
import com.sandip.fynd_movie_test.databinding.MovieItemLayoutBinding
import com.sandip.fynd_movie_test.model.data.Movie
import kotlinx.android.synthetic.main.fragment_home.view.*

class MovieAdapter(private var list:ArrayList<Movie> ) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movieList = ArrayList<Movie>()
    init {
        this.movieList.addAll(list)
    }
    class MovieViewHolder(var layoutBinding: MovieItemLayoutBinding) :
        RecyclerView.ViewHolder(layoutBinding.root) {
        fun bind(movie : Movie){
            layoutBinding.movie = movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieItemLayoutBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context),
            R.layout.movie_item_layout, parent, false)
            return MovieViewHolder(itemMovieItemLayoutBinding as MovieItemLayoutBinding)
    }

    override fun getItemCount(): Int {
       return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    fun addList(movieList:List<Movie>){
        val currentSize = this.movieList.size
        this.movieList.addAll(movieList)
        notifyItemRangeInserted(currentSize, this.movieList.size)
    }

    fun showNewData(movieList:List<Movie>?){
        this.movieList.clear()
        movieList?.let {
            this.movieList.addAll(it)
        }

        notifyDataSetChanged()
    }
}