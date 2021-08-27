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
import com.example.moviecity.databinding.CastListItemBinding
import com.example.moviecity.models.Cast
import com.example.moviecity.utils.ImageHelper

class CastAdapter(private val listener: CastItemCLicked) :
    RecyclerView.Adapter<CastAdapter.PopularMovieViewHolder>() {

    private val items: ArrayList<Cast> = ArrayList()

    class PopularMovieViewHolder(val binding: CastListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {

        val view = CastListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewHolder = PopularMovieViewHolder(view)

        view.root.setOnClickListener {
            listener.onCastItemClicked(items[viewHolder.absoluteAdapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        val currentItem = items[position]

        val imageUrl = "https://image.tmdb.org/t/p/w500" + currentItem.profile_path

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
                    holder.binding.castImage.setImageBitmap(currentItem.bitmap)
                }

                override fun onLoadCleared(@Nullable placeholder: Drawable?) {

                }
            })

        holder.binding.castName.text = currentItem.name

    }

    fun updateCast(cast: List<Cast>) {
        items.clear()
        items.addAll(cast)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

interface CastItemCLicked {
    fun onCastItemClicked(item: Cast)
}