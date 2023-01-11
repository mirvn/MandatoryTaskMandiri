package com.practice.mandatorytaskmandiri.ui.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practice.mandatorytaskmandiri.data.model.GenreModel
import com.practice.mandatorytaskmandiri.databinding.GenreItemRvBinding

class GenreListAdapter : ListAdapter<GenreModel, GenreListAdapter.GenresViewHolder>(diffCallBack) {
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
            view.tvGenreItem.text = model.name
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
