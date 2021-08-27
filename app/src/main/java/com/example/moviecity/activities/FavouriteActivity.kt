package com.example.moviecity.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecity.adapters.FavouriteAdapter
import com.example.moviecity.adapters.FavouriteItemClicked
import com.example.moviecity.databinding.ActivityFavouriteBinding
import com.example.moviecity.models.Favourite
import com.example.moviecity.networkHelper.NetworkChecker
import com.example.moviecity.viewModel.FavouriteViewModel

class FavouriteActivity : AppCompatActivity(), FavouriteItemClicked {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var favAdapter: FavouriteAdapter
    private lateinit var favViewModel: FavouriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        favAdapter = FavouriteAdapter(this)
        binding.recyclerView.adapter = favAdapter

        favViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(FavouriteViewModel::class.java)


        favViewModel.allMovies.observe(this, {
            if (it.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.noFavMovies.visibility = View.VISIBLE

            }
        })

        setupItemTouchHelper()

        favViewModel.allMovies.observe(this, {
            favAdapter.updateMovies(it)
        })

    }

    private fun setupItemTouchHelper() {

        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                favViewModel.deleteMovie(favAdapter.getSwipedMovie(viewHolder.absoluteAdapterPosition))
                Toast.makeText(applicationContext, "Movie Deleted", Toast.LENGTH_SHORT).show()
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }

    override fun onFavouriteItemClicked(item: Favourite) {
        if (NetworkChecker.isNetworkAvailable(applicationContext)) {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("movieId", item.movieId)
            startActivity(intent)
        } else {
            Toast.makeText(this, "No Internet!!!", Toast.LENGTH_SHORT).show()
        }
    }


}