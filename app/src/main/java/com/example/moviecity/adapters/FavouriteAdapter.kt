package com.example.moviecity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviecity.databinding.FavouriteListItemBinding
import com.example.moviecity.models.Favourite

class FavouriteAdapter(private val listener: FavouriteItemClicked) :
    RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    private val allMovies: ArrayList<Favourite> = ArrayList()

    class FavouriteViewHolder(val binding: FavouriteListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {

        val view =
            FavouriteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = FavouriteViewHolder(view)

        view.root.setOnClickListener {
            listener.onFavouriteItemClicked(allMovies[viewHolder.absoluteAdapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val currItem = allMovies[position]

        holder.binding.title.text = currItem.title
        val imageUrl = "https://image.tmdb.org/t/p/w500" + currItem.poster_path

        Glide.with(holder.itemView.context).load(imageUrl)
            .transform(CenterCrop(), RoundedCorners(40)).into(holder.binding.poster)

        holder.binding.description.text = currItem.overview

        holder.binding.ratingPoints.text = currItem.vote_average

    }

    fun updateMovies(updateMovies: List<Favourite>) {
        allMovies.clear()
        allMovies.addAll(updateMovies)

        notifyDataSetChanged()
    }

    fun getSwipedMovie(position: Int): Favourite {
        return allMovies[position]

    }

    override fun getItemCount(): Int {
        return allMovies.size
    }

}

interface FavouriteItemClicked {
    fun onFavouriteItemClicked(item: Favourite)
}
