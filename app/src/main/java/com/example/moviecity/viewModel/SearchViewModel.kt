package com.example.moviecity.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecity.models.SearchResultList
import com.example.moviecity.repository.SearchRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val searchRepo: SearchRepo) : ViewModel() {

    private val searchLiveData: MutableLiveData<SearchResultList> = MutableLiveData()

    fun searchLiveDataObserver(): LiveData<SearchResultList> {
        return searchLiveData
    }


    fun getSearchedMovie(query: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val call: Call<SearchResultList> = searchRepo.getSearchList(query)

            call.enqueue(object : Callback<SearchResultList> {
                override fun onResponse(
                    call: Call<SearchResultList>,
                    response: Response<SearchResultList>
                ) {
                    response.body()?.let {
                        searchLiveData.postValue(it)
                    }

                }

                override fun onFailure(call: Call<SearchResultList>, t: Throwable) {
                    Log.d(ContentValues.TAG, "Error Occurred")
                    searchLiveData.postValue(null)
                }
            })
        }

    }


}