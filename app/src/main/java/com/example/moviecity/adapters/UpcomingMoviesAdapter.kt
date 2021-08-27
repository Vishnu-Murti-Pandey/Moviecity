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
import com.example.moviecity.databinding.ActivityUpcomingMoviesListBinding
import com.example.moviecity.models.UpcomingMovies
import com.example.moviecity.utils.ImageHelper

class UpcomingMoviesAdapter(private val listener: UpcomingMovieItemClicked) :
    RecyclerView.Adapter<UpcomingMoviesAdapter.UpcomingMovieViewHolder>() {

    class UpcomingMovieViewHolder(val binding: ActivityUpcomingMoviesListBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val items: ArrayList<UpcomingMovies> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMovieViewHolder {

        val view = ActivityUpcomingMoviesListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewHolder = UpcomingMovieViewHolder(view)

        view.root.setOnClickListener {
            listener.onUpcomingMovieItemClicked(items[viewHolder.absoluteAdapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: UpcomingMovieViewHolder, position: Int) {
        val currentItem = items[position]

        holder.binding.movieName.text = currentItem.title

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

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateUpcomingMovie(updatedMovie: List<UpcomingMovies>) {
        items.clear()
        items.addAll(updatedMovie)

        notifyDataSetChanged()
    }

}

interface UpcomingMovieItemClicked {
    fun onUpcomingMovieItemClicked(item: UpcomingMovies)
}