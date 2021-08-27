package com.example.moviecity.fragments

import android.content.ContentValues.TAG
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
import com.example.moviecity.databinding.FragmentPopularMovieBinding
import com.example.moviecity.networkHelper.NetworkChecker
import com.example.moviecity.networkRequest.MovieService
import com.example.moviecity.repository.PopularFragRepo
import com.example.moviecity.viewModel.PopularMovieViewModel
import com.example.moviecity.viewModel.viewModelFactory.PopularFragVMFactory


class PopularMovieFragment : Fragment() {

    private lateinit var binding: FragmentPopularMovieBinding
    private lateinit var mAdapter: ViewAllAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate goes")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularMovieBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val popularDao = context?.let { MoviesDatabase.getDatabase(it).popularFragDao() }

        val popularRepo = popularDao?.let { PopularFragRepo(it, apiInstance = MovieService) }

        val viewModel: PopularMovieViewModel = ViewModelProvider(
            this,
            PopularFragVMFactory(popularRepo!!)
        ).get(PopularMovieViewModel::class.java)

        binding.recyclerFragPopular.layoutManager = LinearLayoutManager(context)
        setAdapter(viewModel)
        mAdapter = ViewAllAdapter()

        binding.recyclerFragPopular.adapter = mAdapter

    }

    private fun setAdapter(viewModel: PopularMovieViewModel) {
        if (!NetworkChecker.isNetworkAvailable(context)) {
            viewModel.getPopularData().observe(viewLifecycleOwner, {
                mAdapter.updateMovie(it.results)
            })
        } else {
            initPopularVM(viewModel)
        }
    }

    private fun initPopularVM(viewModel: PopularMovieViewModel) {

        viewModel.getPopularViewAllMovies()
        viewModel.viewAllPopularLiveDataObserver().observe(viewLifecycleOwner, {
            if (it != null) {
                mAdapter.updateMovie(it.results)
                viewModel.insertPopularData(it)
            } else {
                Toast.makeText(context, "Error In Fetching data...", Toast.LENGTH_SHORT).show()
            }
        })
    }


}