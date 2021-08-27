package com.example.moviecity.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecity.models.MovieDetails
import com.example.moviecity.repository.DetailsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(private val detailsRepo: DetailsRepo) : ViewModel() {

    private val detailsLiveData: MutableLiveData<MovieDetails> = MutableLiveData()

    fun detailsLiveDataObserver(): LiveData<MovieDetails> {
        return detailsLiveData
    }

    fun insertDetailsMovieData(data: MovieDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsRepo.insert(data)
        }
    }

    fun getDetailsMovieData(): LiveData<MovieDetails> {
        return detailsRepo.fetchAllPopularMovies()
    }


    fun getMovieDetails(id: String?) {

        viewModelScope.launch(Dispatchers.IO) {
            val call: Call<MovieDetails> = detailsRepo.getDetailsRepo(id)

            call.enqueue(object : Callback<MovieDetails> {
                override fun onResponse(
                    call: Call<MovieDetails>,
                    response: Response<MovieDetails>
                ) {
                    response.body()?.let {
                        detailsLiveData.postValue(it)
                    }
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    Log.d(ContentValues.TAG, "Error Occurred")
                    detailsLiveData.postValue(null)
                }

            })
        }


    }

}