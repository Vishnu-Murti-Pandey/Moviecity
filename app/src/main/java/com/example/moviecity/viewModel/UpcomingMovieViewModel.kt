package com.example.moviecity.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecity.models.ViewAllMoviesList
import com.example.moviecity.repository.UpcomingFragRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingMovieViewModel(private val upcomingFragRepo: UpcomingFragRepo) : ViewModel() {

    private var viewAllUpcomingLiveData: MutableLiveData<ViewAllMoviesList> = MutableLiveData()

    fun viewAllUpcomingLiveDataObserver(): LiveData<ViewAllMoviesList> {
        return viewAllUpcomingLiveData
    }


    fun insertUpcomingData(data: ViewAllMoviesList) {
        viewModelScope.launch(Dispatchers.IO) {
            upcomingFragRepo.insert(data)
        }
    }

    fun getUpcomingData(): LiveData<ViewAllMoviesList> {
        return upcomingFragRepo.fetchAllPopularMovies()
    }


    fun getUpcomingViewAllMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            val call: Call<ViewAllMoviesList> = upcomingFragRepo.getAllUpcomingMovie()

            call.enqueue(object : Callback<ViewAllMoviesList> {
                override fun onResponse(
                    call: Call<ViewAllMoviesList>,
                    response: Response<ViewAllMoviesList>
                ) {
                    response.body()?.let {
                        viewAllUpcomingLiveData.postValue(it)
                    }
                }

                override fun onFailure(call: Call<ViewAllMoviesList>, t: Throwable) {
                    Log.d(ContentValues.TAG, "Error Occurred")
                    viewAllUpcomingLiveData.postValue(null)
                }

            })
        }

    }

}