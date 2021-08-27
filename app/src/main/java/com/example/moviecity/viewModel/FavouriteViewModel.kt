package com.example.moviecity.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecity.dataBase.MoviesDatabase
import com.example.moviecity.models.Favourite
import com.example.moviecity.repository.FavouriteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {

    private val favRepo: FavouriteRepo
    val allMovies: LiveData<List<Favourite>>

    init {
        val favDao = MoviesDatabase.getDatabase(application).favouriteDao()
        favRepo = FavouriteRepo(favDao)
        allMovies = favRepo.allMovies
    }

    fun deleteMovie(item: Favourite) {
        viewModelScope.launch(Dispatchers.IO) {
            favRepo.delete(item)
        }
    }

    fun insertMovie(item: Favourite) {
        viewModelScope.launch(Dispatchers.IO) {
            favRepo.insert(item)
        }
    }


}