package com.example.moviecity.activities

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.moviecity.R
import com.example.moviecity.adapters.CastAdapter
import com.example.moviecity.adapters.CastItemCLicked
import com.example.moviecity.dataBase.MoviesDatabase
import com.example.moviecity.databinding.ActivityDetailsBinding
import com.example.moviecity.models.Cast
import com.example.moviecity.models.Favourite
import com.example.moviecity.models.MovieDetails
import com.example.moviecity.networkHelper.NetworkChecker
import com.example.moviecity.networkRequest.MovieService
import com.example.moviecity.repository.DetailsRepo
import com.example.moviecity.utils.ImageHelper
import com.example.moviecity.viewModel.DetailsViewModel
import com.example.moviecity.viewModel.FavouriteViewModel
import com.example.moviecity.viewModel.viewModelFactory.DetailsVMFactory

class DetailsActivity : AppCompatActivity(), CastItemCLicked {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var castAdapter: CastAdapter
    private var isLiked: Boolean = false
    private var dupMovieId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        val movieId: String? = bundle?.getString("movieId")
        if (movieId != null) {
            dupMovieId = movieId
        }

        Log.w(TAG, "OhYeah!!!$movieId")

        val favViewModel = FavouriteViewModel(application)

        val detailsDao = MoviesDatabase.getDatabase(applicationContext).detailsDao()

        val detailsRepo = DetailsRepo(detailsDao, apiInstance = MovieService)
        val detailsViewModel: DetailsViewModel =
            ViewModelProvider(this, DetailsVMFactory(detailsRepo)).get(DetailsViewModel::class.java)

        if (!NetworkChecker.isNetworkAvailable(applicationContext)) {
            detailsViewModel.getDetailsMovieData().observe(this, {
                setData(it)
            })
        } else {
            initDetailsViewModels(detailsViewModel, movieId)
        }

        binding.fab.setOnClickListener {

            val fav: Favourite = getMovieDetailsForFav(detailsViewModel)

            if (!isLiked) {
                isLiked = true
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fab.context,
                        R.drawable.ic_baseline_favorite_24
                    )
                )
                insertFavViewModel(fav, favViewModel)
                Log.w(TAG, "OhYeah!!! Movie added to favourites")
                Toast.makeText(this, "Movie added to favourites...", Toast.LENGTH_SHORT).show()

            } else {
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fab.context,
                        R.drawable.ic_baseline_favorite_border_24
                    )
                )
                deleteFavViewModel(fav, favViewModel)
                Log.w(TAG, "OhYeah!!! Movie deleted from favourites")
            }
        }

    }

    private fun getMovieDetailsForFav(detailsViewModel: DetailsViewModel): Favourite {

        var favourite = Favourite("", "", false, "", "", "")
        detailsViewModel.detailsLiveDataObserver().observe(this, {
            if (it != null) {
                favourite = Favourite(
                    dupMovieId,
                    it.title,
                    true,
                    it.poster_path,
                    it.vote_average,
                    it.overview
                )
            }
        })
        return favourite
    }

    private fun deleteFavViewModel(favourite: Favourite, favViewModel: FavouriteViewModel) {

        favViewModel.deleteMovie(favourite)
        Log.w(TAG, "Oh Yeah!!! Movie Deleted")
    }


    private fun insertFavViewModel(favourite: Favourite, favViewModel: FavouriteViewModel) {

        favViewModel.insertMovie(favourite)
        Log.w(TAG, "Oh Yeah!!! Movie Inserted")
    }

    private fun initDetailsViewModels(detailsViewModel: DetailsViewModel, movieId: String?) {

        detailsViewModel.getMovieDetails(movieId)

        detailsViewModel.detailsLiveDataObserver().observe(this, {
            if (it != null) {
                Log.w(TAG, "OhYeah!!!, MovieDetails id is not null")
                setData(it)
            } else {
                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setData(movieDetails: MovieDetails) {

        setCastAdapter(movieDetails.credits.cast)

        val imageUrl1 = "https://image.tmdb.org/t/p/w500" + movieDetails.backdrop_path
        toBitmapBackdrop(imageUrl1)

        binding.overView.text = movieDetails.overview

        val imageUrl2 = "https://image.tmdb.org/t/p/w500" + movieDetails.poster_path
        toBitmapPoster(movieDetails, imageUrl2)

        val runtime = movieDetails.runtime + " minutes"
        binding.runtime.text = runtime

        binding.movieName.text = movieDetails.title

        val ratings = movieDetails.vote_average

        if (ratings == "0.0" || ratings == "0" || ratings == "null") {
            binding.ratingLayout.visibility = View.GONE
            binding.releaseDate.visibility = View.VISIBLE
            val date = "Release Date: " + movieDetails.release_date
            binding.releaseDate.text = date
        } else {
            binding.topRating.text = ratings
        }

        val genres = movieDetails.genres

        if (genres.size >= 2) {
            binding.genres1.text = genres[0].name
            binding.genres2.text = genres[1].name
        } else if (genres.size == 1) {
            binding.genres1.text = genres[0].name
        }

    }

    private fun toBitmapPoster(movieDetails: MovieDetails, imageUrl: String) {

        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .transform(CenterCrop(), RoundedCorners(60))
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    movieDetails.bitmapPoster =
                        ImageHelper().getRoundedCornerBitmap(resource, 60)
                    binding.poster.setImageBitmap(movieDetails.bitmapPoster)
                }

                override fun onLoadCleared(@Nullable placeholder: Drawable?) {

                }
            })

    }

    private fun toBitmapBackdrop(imageUrl: String) {

        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .override(900, 500)
            .centerCrop()
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    binding.backdrop.setImageBitmap(resource)
                }

                override fun onLoadCleared(@Nullable placeholder: Drawable?) {

                }
            })

    }

    private fun setCastAdapter(cast: List<Cast>) {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        castAdapter = CastAdapter(this)

        castAdapter.updateCast(cast)

        Log.w(TAG, "OhYeah!!! Cast Adapter Set")
        binding.recyclerView.adapter = castAdapter
    }

    override fun onCastItemClicked(item: Cast) {
        Toast.makeText(this, "Image Clicked", Toast.LENGTH_SHORT).show()
    }


}