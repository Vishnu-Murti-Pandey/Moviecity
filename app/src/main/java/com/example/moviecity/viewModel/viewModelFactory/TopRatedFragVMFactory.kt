package com.example.moviecity.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecity.repository.TopRatedFragRepo
import com.example.moviecity.viewModel.TopRatedMovieViewModel

class TopRatedFragVMFactory(private val topRatedRepo: TopRatedFragRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TopRatedMovieViewModel(topRatedRepo) as T
    }


}