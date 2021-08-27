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
import com.example.moviecity.databinding.ActivityTopRatedMoviesListBinding
import com.example.moviecity.models.TopRatedMovies
import com.example.moviecity.utils.ImageHelper

class TopRatedMoviesAdapter(private val listener: TopRatedMovieItemClicked) :
    RecyclerView.Adapter<TopRatedMoviesAdapter.TopRatedMovieViewHolder>() {


    private val items: ArrayList<TopRatedMovies> = ArrayList()

    class TopRatedMovieViewHolder(val binding: ActivityTopRatedMoviesListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedMovieViewHolder {

        val view = ActivityTopRatedMoviesListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewHolder = TopRatedMovieViewHolder(view)

        view.root.setOnClickListener {
            listener.onTopRatedMovieItemClicked(items[viewHolder.absoluteAdapterPosition])
        }
        return viewHolder

    }

    override fun onBindViewHolder(holder: TopRatedMovieViewHolder, position: Int) {

        val currentItem = items[position]

        val movieName = currentItem.title
        var movieNameTrimmed = ""

        for (i in movieName.indices) {
            movieNameTrimmed = if (movieName[i] == ':' || movieName[i] == '!') {
                break
            } else {
                movieNameTrimmed + movieName[i]
            }
        }

        holder.binding.movieName.text = movieNameTrimmed

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
                    holder.binding.ivPoster.setImageBitmap(currentItem.bitmap)
                }

                override fun onLoadCleared(@Nullable placeholder: Drawable?) {

                }
            })

        holder.binding.topRating.text = currentItem.vote_average
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateTopRatedMovie(updatedMovie: List<TopRatedMovies>) {
        items.clear()
        items.addAll(updatedMovie)

        notifyDataSetChanged()
    }

}

interface TopRatedMovieItemClicked {
    fun onTopRatedMovieItemClicked(item: TopRatedMovies)
}