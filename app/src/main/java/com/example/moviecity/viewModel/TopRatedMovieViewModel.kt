package com.example.moviecity.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecity.models.ViewAllMoviesList
import com.example.moviecity.repository.TopRatedFragRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopRatedMovieViewModel(private val topRatedFragRepo: TopRatedFragRepo) : ViewModel() {

    private var viewAllTopRatedLiveData: MutableLiveData<ViewAllMoviesList> = MutableLiveData()

    fun viewAllTopRatedLiveDataObserver(): LiveData<ViewAllMoviesList> {
        return viewAllTopRatedLiveData
    }

    fun insertTopRatedData(data: ViewAllMoviesList) {
        viewModelScope.launch(Dispatchers.IO) {
            topRatedFragRepo.insert(data)
        }
    }

    fun getTopRatedData(): LiveData<ViewAllMoviesList> {
        return topRatedFragRepo.fetchAllPopularMovies()
    }


    fun getTopRatedViewAllMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            val call: Call<ViewAllMoviesList> = topRatedFragRepo.getAllTopRatedMovie()

            call.enqueue(object : Callback<ViewAllMoviesList> {
                override fun onResponse(
                    call: Call<ViewAllMoviesList>,
                    response: Response<ViewAllMoviesList>
                ) {
                    response.body()?.let {
                        viewAllTopRatedLiveData.postValue(it)
                    }
                }

                override fun onFailure(call: Call<ViewAllMoviesList>, t: Throwable) {
                    Log.d(ContentValues.TAG, "Error Occurred")
                    viewAllTopRatedLiveData.postValue(null)
                }

            })
        }

    }

}