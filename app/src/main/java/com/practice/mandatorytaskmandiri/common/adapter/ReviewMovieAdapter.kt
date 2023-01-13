package com.practice.mandatorytaskmandiri.common.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.mandatorytaskmandiri.BuildConfig
import com.practice.mandatorytaskmandiri.data.model.MovieReviewModel
import com.practice.mandatorytaskmandiri.databinding.UserReviewItemRvBinding

class ReviewMovieAdapter(private val navController: NavController) :
    ListAdapter<MovieReviewModel.Result, ReviewMovieAdapter.ReviewMovieViewHolder>(
        diffCallBack
    ) {
    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<MovieReviewModel.Result>() {
            override fun areItemsTheSame(
                oldItem: MovieReviewModel.Result,
                newItem: MovieReviewModel.Result
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieReviewModel.Result,
                newItem: MovieReviewModel.Result
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ReviewMovieViewHolder(private val view: UserReviewItemRvBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bindImage(model: MovieReviewModel.Result) {
            Log.e("TAG", "bindImage:$model ")
            view.apply {
                Glide.with(itemView)
                    .load(BuildConfig.URL_TMDB_IMAGE + model.authorDetails?.avatarPath)
                    .into(this.circleImageView)
                this.tvAuthor.text = model.author
                this.tvCreatedAt.text = model.createdAt?.substringBefore("T")
                this.tvContent.text = model.content
                this.tvRating.text = model.authorDetails?.rating.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewMovieViewHolder {
        val reviewItem = UserReviewItemRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewMovieViewHolder(reviewItem)
    }

    override fun onBindViewHolder(holder: ReviewMovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.bindImage(data)
    }
}
