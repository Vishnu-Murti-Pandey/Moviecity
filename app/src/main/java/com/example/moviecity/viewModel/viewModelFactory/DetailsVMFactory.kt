package com.example.moviecity.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecity.repository.DetailsRepo
import com.example.moviecity.viewModel.DetailsViewModel

class DetailsVMFactory(
    private val detailsRepo: DetailsRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(detailsRepo) as T
    }
}