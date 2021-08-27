package com.example.moviecity.activities

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecity.adapters.SearchMovieAdapter
import com.example.moviecity.adapters.SearchMovieItemClicked
import com.example.moviecity.databinding.ActivitySearchBinding
import com.example.moviecity.models.SearchMovie
import com.example.moviecity.networkRequest.MovieService
import com.example.moviecity.repository.SearchRepo
import com.example.moviecity.viewModel.SearchViewModel
import com.example.moviecity.viewModel.viewModelFactory.SearchVMFactory

class SearchActivity : AppCompatActivity(), SearchMovieItemClicked {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchRepo = SearchRepo(apiInstance = MovieService)

        val searchViewModel: SearchViewModel =
            ViewModelProvider(this, SearchVMFactory(searchRepo)).get(SearchViewModel::class.java)


        binding.btSearch.setOnClickListener {
            closeKeyboard()
            binding.progressBar.visibility = View.VISIBLE
            val searchText = binding.searchBar.text.toString()

            if (searchText.isNotEmpty()) {
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                initSearchViewModel(searchViewModel, searchText)
                searchAdapter = SearchMovieAdapter(this)
                binding.recyclerView.adapter = searchAdapter
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(this, "Enter valid name..", Toast.LENGTH_SHORT).show()
            }

        }

    }


    private fun initSearchViewModel(searchViewModel: SearchViewModel, searchText: String) {

        searchViewModel.getSearchedMovie(searchText)

        searchViewModel.searchLiveDataObserver().observe(this, {
            if (it != null) {
                searchAdapter.updateMovies(it.results)
                binding.progressBar.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

    }

    override fun onSearchMovieItemClicked(item: SearchMovie) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("movieId", item.id)
        Log.w(TAG, "Vishnu" + item.id)
        closeKeyboard()
        startActivity(intent)
    }


}