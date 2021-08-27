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
import com.example.moviecity.databinding.FragmentUpcomingMovieBinding
import com.example.moviecity.networkHelper.NetworkChecker
import com.example.moviecity.networkRequest.MovieService
import com.example.moviecity.repository.UpcomingFragRepo
import com.example.moviecity.viewModel.UpcomingMovieViewModel
import com.example.moviecity.viewModel.viewModelFactory.UpcomingFragVMFactory


class UpcomingMovieFragment : Fragment() {

    private lateinit var binding: FragmentUpcomingMovieBinding
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

        binding = FragmentUpcomingMovieBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(ContentValues.TAG, "onViewCreated goes")

        val upcomingDao = context?.let { MoviesDatabase.getDatabase(it).upcomingFragDao() }

        val upcomingRepo = upcomingDao?.let { UpcomingFragRepo(it, apiInstance = MovieService) }

        val viewModel: UpcomingMovieViewModel =
            ViewModelProvider(this, UpcomingFragVMFactory(upcomingRepo!!)).get(
                UpcomingMovieViewModel::class.java
            )

        binding.recyclerFragUpcoming.layoutManager = LinearLayoutManager(context)
        setAdapter(viewModel)
        mAdapter = ViewAllAdapter()

        binding.recyclerFragUpcoming.adapter = mAdapter

    }

    private fun setAdapter(viewModel: UpcomingMovieViewModel) {
        if (!NetworkChecker.isNetworkAvailable(context)) {
            viewModel.getUpcomingData().observe(viewLifecycleOwner, {
                mAdapter.updateMovie(it.results)
            })
        } else {
            initUpcomingVM(viewModel)
        }
    }

    private fun initUpcomingVM(viewModel: UpcomingMovieViewModel) {

        viewModel.getUpcomingViewAllMovies()

        viewModel.viewAllUpcomingLiveDataObserver().observe(viewLifecycleOwner, {
            if (it != null) {
                mAdapter.updateMovie(it.results)
                viewModel.insertUpcomingData(it)
            } else {
                Toast.makeText(context, "Error In Fetching data...", Toast.LENGTH_SHORT).show()
            }
        })

    }

}