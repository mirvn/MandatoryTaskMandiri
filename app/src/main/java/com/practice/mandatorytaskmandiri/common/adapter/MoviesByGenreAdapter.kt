package com.practice.mandatorytaskmandiri.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.mandatorytaskmandiri.BuildConfig
import com.practice.mandatorytaskmandiri.data.model.MovieByGenreModel
import com.practice.mandatorytaskmandiri.databinding.MovieByGenreItemBinding

class MoviesByGenreAdapter(private val navController: NavController) :
    ListAdapter<MovieByGenreModel.Result, MoviesByGenreAdapter.GenresViewHolder>(
        diffCallBack
    ) {
    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<MovieByGenreModel.Result>() {
            override fun areItemsTheSame(
                oldItem: MovieByGenreModel.Result,
                newItem: MovieByGenreModel.Result
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieByGenreModel.Result,
                newItem: MovieByGenreModel.Result
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    inner class GenresViewHolder(private val view: MovieByGenreItemBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bindImage(model: MovieByGenreModel.Result) {
//            val bundle = Bundle() // Bundle to pass genreId
//            bundle.putParcelable(itemView.context.getString(R.string.genre_model_key), model)
            view.imageMovieByGenreItem.apply {
                Glide.with(itemView).load(BuildConfig.URL_TMDB_IMAGE + model.posterPath).into(this)
                setOnClickListener {
                    // navController.navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val genresItem = MovieByGenreItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GenresViewHolder(genresItem)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val data = getItem(position)
        holder.bindImage(data)
    }
}
