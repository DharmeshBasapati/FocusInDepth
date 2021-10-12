package com.app.focusindepth.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.focusindepth.models.News
import com.app.focusindepth.models.NewsResponse
import com.app.focusindepth.network.RetrofitBuilder
import com.app.focusindepth.repository.FocusRepositoryImpl
import com.app.focusindepth.room.dao.FocusDao
import com.app.focusindepth.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel(focusDao: FocusDao) : ViewModel() {

    private val TAG = "NewsViewModel"

    var focusRepository: FocusRepositoryImpl = FocusRepositoryImpl.getInstance(focusDao)

    private var allNews = MutableLiveData<Resource<List<News>>>()

    fun getNewsList(): LiveData<Resource<List<News>>> = allNews

    fun fetchNewsForSelectedCategory(categoryName: String) {

        viewModelScope.launch(Dispatchers.IO) {

//            allNews.postValue(
//                focusRepository.getNewsFromCategory(categoryName).value
//            )

            allNews.postValue(Resource.loading(null))

            RetrofitBuilder.apiServices.getNewsForThisCategory(categoryName).enqueue(object :
                Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    allNews.postValue(Resource.success(response.body()?.data))
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                    allNews.postValue(Resource.error(null, t.message))
                }
            })

        }

    }

}