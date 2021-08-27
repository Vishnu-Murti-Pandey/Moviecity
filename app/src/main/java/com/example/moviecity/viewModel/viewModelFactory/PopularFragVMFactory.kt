package com.example.moviecity.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecity.repository.PopularFragRepo
import com.example.moviecity.viewModel.PopularMovieViewModel

class PopularFragVMFactory(private val popularRepo: PopularFragRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PopularMovieViewModel(popularRepo) as T
    }


}