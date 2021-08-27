package com.example.moviecity.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.moviecity.databinding.ActivityViewAllListBinding
import com.example.moviecity.models.Movies
import com.example.moviecity.utils.ImageHelper

class ViewAllAdapter : RecyclerView.Adapter<ViewAllAdapter.ViewAllMovieHolder>() {

    private val moviesList: ArrayList<Movies> = ArrayList()

    class ViewAllMovieHolder(val binding: ActivityViewAllListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllMovieHolder {
        val view =
            ActivityViewAllListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewAllMovieHolder(view)
    }

    override fun onBindViewHolder(holder: ViewAllMovieHolder, position: Int) {
        val currentItem = moviesList[position]

        holder.binding.title.text = currentItem.title

        val imageUrl = "https://image.tmdb.org/t/p/w500" + currentItem.poster_path

        Glide.with(holder.itemView.context)
            .asBitmap()
            .load(imageUrl)
            .transform(CenterCrop(), RoundedCorners(60))
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    currentItem.bitmap = ImageHelper().getRoundedCornerBitmap(resource, 60)
                    holder.binding.poster.setImageBitmap(currentItem.bitmap)
                }

                override fun onLoadCleared(@Nullable placeholder: Drawable?) {

                }
            })

        holder.binding.description.text = currentItem.overview

        holder.binding.ratingPoints.text = currentItem.vote_average
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun updateMovie(updatedMovie: List<Movies>) {
        moviesList.clear()
        moviesList.addAll(updatedMovie)

        notifyDataSetChanged()
    }

}