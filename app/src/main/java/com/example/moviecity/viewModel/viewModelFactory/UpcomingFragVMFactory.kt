package com.example.moviecity.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecity.repository.UpcomingFragRepo
import com.example.moviecity.viewModel.UpcomingMovieViewModel

class UpcomingFragVMFactory(private val upcomingRepo: UpcomingFragRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UpcomingMovieViewModel(upcomingRepo) as T
    }
}