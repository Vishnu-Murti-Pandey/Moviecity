package com.example.moviecity.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecity.repository.SearchRepo
import com.example.moviecity.viewModel.SearchViewModel

class SearchVMFactory(private val searchRepo: SearchRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(searchRepo) as T
    }
}