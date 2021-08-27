package com.example.moviecity.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecity.models.PopularResultList
import com.example.moviecity.models.TopRatedResultList
import com.example.moviecity.models.UpcomingResultList
import com.example.moviecity.repository.MainPopularRepo
import com.example.moviecity.repository.MainTopRatedRepo
import com.example.moviecity.repository.MainUpcomingRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val popularRepo: MainPopularRepo,
    private val upcomingRepo: MainUpcomingRepo,
    private val topRatedRepo: MainTopRatedRepo
) : ViewModel() {

    private var popularLiveData: MutableLiveData<PopularResultList> = MutableLiveData()
    private var upcomingLiveData: MutableLiveData<UpcomingResultList> = MutableLiveData()
    private var topRatedLiveData: MutableLiveData<TopRatedResultList> = MutableLiveData()


    fun popularMovieLiveDataObserver(): LiveData<PopularResultList> {
        return popularLiveData
    }

    fun upcomingMovieLiveDataObserver(): LiveData<UpcomingResultList> {
        return upcomingLiveData
    }

    fun topRatedMovieLiveDataObserver(): LiveData<TopRatedResultList> {
        return topRatedLiveData
    }

    fun insertPopularData(data: PopularResultList) {
        viewModelScope.launch(Dispatchers.IO) {
            popularRepo.insert(data)
        }
    }

    fun insertUpcomingData(data: UpcomingResultList) {
        viewModelScope.launch(Dispatchers.IO) {
            upcomingRepo.insert(data)
        }
    }

    fun insertTopRatedData(data: TopRatedResultList) {
        viewModelScope.launch(Dispatchers.IO) {
            topRatedRepo.insert(data)
        }
    }

    fun getPopularData(): LiveData<PopularResultList> {
        return popularRepo.fetchAllPopularMovies()
    }

    fun getUpcomingData(): LiveData<UpcomingResultList> {
        return upcomingRepo.fetchAllUpcomingMovies()
    }

    fun getTopRatedData(): LiveData<TopRatedResultList> {
        return topRatedRepo.fetchAllTopRatedMovies()
    }


    fun getPopularMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            val call: Call<PopularResultList> = popularRepo.getPopularMovie()

            call.enqueue(object : Callback<PopularResultList> {
                override fun onResponse(
                    call: Call<PopularResultList>,
                    response: Response<PopularResultList>
                ) {
                    response.body()?.let {
                        popularLiveData.postValue(it)
                    }
                }

                override fun onFailure(call: Call<PopularResultList>, t: Throwable) {
                    Log.d(ContentValues.TAG, "Error Occurred")
                    popularLiveData.postValue(null)
                }

            })
        }
    }


    fun getUpcomingMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            val call: Call<UpcomingResultList> = upcomingRepo.getUpcomingMovie()

            call.enqueue(object : Callback<UpcomingResultList> {
                override fun onResponse(
                    call: Call<UpcomingResultList>,
                    response: Response<UpcomingResultList>
                ) {
                    response.body()?.let {
                        upcomingLiveData.postValue(it)
                    }
                }

                override fun onFailure(call: Call<UpcomingResultList>, t: Throwable) {
                    Log.d(ContentValues.TAG, "Error Occurred")
                    upcomingLiveData.postValue(null)
                }

            })
        }
    }


    fun getTopRatedMovies() {

        viewModelScope.launch(Dispatchers.IO) {
            val call: Call<TopRatedResultList> = topRatedRepo.getTopRatedMovie()

            call.enqueue(object : Callback<TopRatedResultList> {
                override fun onResponse(
                    call: Call<TopRatedResultList>,
                    response: Response<TopRatedResultList>
                ) {
                    response.body()?.let {
                        topRatedLiveData.postValue(it)
                    }
                }

                override fun onFailure(call: Call<TopRatedResultList>, t: Throwable) {
                    Log.d(ContentValues.TAG, "Error Occurred")
                    topRatedLiveData.postValue(null)
                }

            })
        }


    }

}