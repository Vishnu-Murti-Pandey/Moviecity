package com.example.moviecity.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecity.repository.MainPopularRepo
import com.example.moviecity.repository.MainTopRatedRepo
import com.example.moviecity.repository.MainUpcomingRepo
import com.example.moviecity.viewModel.MainViewModel

class MainVMFactory(
    private val popularRepo: MainPopularRepo,
    private val upcomingRepo: MainUpcomingRepo,
    private val topRatedRepo: MainTopRatedRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(popularRepo, upcomingRepo, topRatedRepo) as T
    }


}