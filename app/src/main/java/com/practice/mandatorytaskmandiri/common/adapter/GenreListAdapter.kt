package com.practice.mandatorytaskmandiri.common.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practice.mandatorytaskmandiri.R
import com.practice.mandatorytaskmandiri.data.model.GenreModel
import com.practice.mandatorytaskmandiri.databinding.GenreItemRvBinding

class GenreListAdapter(private val navController: NavController) :
    ListAdapter<GenreModel, GenreListAdapter.GenresViewHolder>(
        diffCallBack
    ) {
    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<GenreModel>() {
            override fun areItemsTheSame(oldItem: GenreModel, newItem: GenreModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GenreModel, newItem: GenreModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class GenresViewHolder(val view: GenreItemRvBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(model: GenreModel) {
            val bundle = Bundle() // Bundle to pass genreId
            bundle.putParcelable(itemView.context.getString(R.string.genre_model_key), model)
            view.tvGenreItem.text = model.name
            view.imageButton.setOnClickListener {
                navController.navigate(R.id.action_GenreListFragment_to_MoviesByGenreFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val genresItem = GenreItemRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GenresViewHolder(genresItem)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}
