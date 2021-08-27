package com.example.moviecity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviecity.databinding.SearchItemListBinding
import com.example.moviecity.models.SearchMovie

class SearchMovieAdapter(private val listener: SearchMovieItemClicked) :
    RecyclerView.Adapter<SearchMovieAdapter.SearchMoviesViewHolder>() {

    private val items: ArrayList<SearchMovie> = ArrayList()

    class SearchMoviesViewHolder(val binding: SearchItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesViewHolder {
        val view = SearchItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = SearchMoviesViewHolder(view)

        view.root.setOnClickListener {
            listener.onSearchMovieItemClicked(items[viewHolder.absoluteAdapterPosition])
        }
        return viewHolder
    }

    fun updateMovies(updatedMovies: List<SearchMovie>) {
        items.clear()
        items.addAll(updatedMovies)

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SearchMoviesViewHolder, position: Int) {
        val currentItem = items[position]

        holder.binding.title.text = currentItem.title
        val imageUrl = "https://image.tmdb.org/t/p/w500" + currentItem.poster_path

        Glide.with(holder.itemView.context).load(imageUrl)
            .transform(CenterCrop(), RoundedCorners(10)).into(holder.binding.imagePoster)


    }

    override fun getItemCount(): Int {
        return items.size
    }

}

interface SearchMovieItemClicked {
    fun onSearchMovieItemClicked(item: SearchMovie)
}