package com.app.focusindepth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.focusindepth.models.News
import com.app.focusindepth.repository.FocusRepositoryImpl
import com.app.focusindepth.room.dao.FocusDao
import com.app.focusindepth.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(focusDao: FocusDao) : ViewModel() {

    var focusRepository: FocusRepositoryImpl = FocusRepositoryImpl.getInstance(focusDao)

    private var allNews: LiveData<Resource<List<News>>>

    fun getNewsList(): LiveData<Resource<List<News>>> = allNews

    init {
        allNews = MutableLiveData()
    }

    fun fetchNewsForSelectedCategory(categoryName: String) {

        focusRepository.getNewsFromCategory(categoryName)
        allNews = focusRepository.getNewsResponse()

    }

    fun addNewsToReadLater(news: News) {
        viewModelScope.launch(Dispatchers.IO) {
            focusRepository.addNewsToReadLater(news)
        }
    }

}