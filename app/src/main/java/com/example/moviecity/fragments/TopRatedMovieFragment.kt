package com.example.moviecity.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecity.adapters.ViewAllAdapter
import com.example.moviecity.dataBase.MoviesDatabase
import com.example.moviecity.databinding.FragmentTopRatedMovieBinding
import com.example.moviecity.networkHelper.NetworkChecker
import com.example.moviecity.networkRequest.MovieService
import com.example.moviecity.repository.TopRatedFragRepo
import com.example.moviecity.viewModel.TopRatedMovieViewModel
import com.example.moviecity.viewModel.viewModelFactory.TopRatedFragVMFactory


class TopRatedMovieFragment : Fragment() {

    private lateinit var binding: FragmentTopRatedMovieBinding
    private lateinit var mAdapter: ViewAllAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(ContentValues.TAG, "onCreate goes")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(ContentValues.TAG, "onCreateView goes")

        binding = FragmentTopRatedMovieBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(ContentValues.TAG, "onViewCreated goes")

        val topRatedDao = context?.let { MoviesDatabase.getDatabase(it).topRatedFragDao() }

        val topRatedRepo = topRatedDao?.let { TopRatedFragRepo(it, apiInstance = MovieService) }

        val viewModel: TopRatedMovieViewModel =
            ViewModelProvider(this, TopRatedFragVMFactory(topRatedRepo!!)).get(
                TopRatedMovieViewModel::class.java
            )

        binding.recyclerFragTopRated.layoutManager = LinearLayoutManager(context)
        setAdapter(viewModel)
        mAdapter = ViewAllAdapter()

        binding.recyclerFragTopRated.adapter = mAdapter

    }

    private fun setAdapter(viewModel: TopRatedMovieViewModel) {
        if (!NetworkChecker.isNetworkAvailable(context)) {
            viewModel.getTopRatedData().observe(viewLifecycleOwner, {
                mAdapter.updateMovie(it.results)
            })
        } else {
            initTopRatedVM(viewModel)
        }
    }


    private fun initTopRatedVM(viewModel: TopRatedMovieViewModel) {

        viewModel.getTopRatedViewAllMovies()

        viewModel.viewAllTopRatedLiveDataObserver().observe(viewLifecycleOwner, {
            if (it != null) {
                mAdapter.updateMovie(it.results)
                viewModel.insertTopRatedData(it)
            } else {
                Toast.makeText(context, "Error In Fetching data...", Toast.LENGTH_SHORT).show()
            }
        })

    }


}