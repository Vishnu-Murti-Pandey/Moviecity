package com.example.moviecity.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecity.models.ViewAllMoviesList
import com.example.moviecity.repository.PopularFragRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularMovieViewModel(private val popFragRepo: PopularFragRepo) : ViewModel() {

    private var viewAllPopularLiveData: MutableLiveData<ViewAllMoviesList> = MutableLiveData()

    fun viewAllPopularLiveDataObserver(): LiveData<ViewAllMoviesList> {
        return viewAllPopularLiveData
    }

    fun insertPopularData(data: ViewAllMoviesList) {
        viewModelScope.launch(Dispatchers.IO) {
            popFragRepo.insert(data)
        }
    }

    fun getPopularData(): LiveData<ViewAllMoviesList> {
        return popFragRepo.fetchAllPopularMovies()
    }


    fun getPopularViewAllMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            val call: Call<ViewAllMoviesList> = popFragRepo.getAllPopularMovie()

            call.enqueue(object : Callback<ViewAllMoviesList> {
                override fun onResponse(
                    call: Call<ViewAllMoviesList>,
                    response: Response<ViewAllMoviesList>
                ) {
                    response.body()?.let {
                        viewAllPopularLiveData.postValue(it)
                    }
                }

                override fun onFailure(call: Call<ViewAllMoviesList>, t: Throwable) {
                    Log.d(ContentValues.TAG, "Error Occurred")
                    viewAllPopularLiveData.postValue(null)
                }

            })
        }
    }


}