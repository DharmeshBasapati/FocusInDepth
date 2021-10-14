package com.app.focusindepth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.focusindepth.repository.FocusRepositoryImpl
import com.app.focusindepth.room.dao.FocusDao
import com.app.focusindepth.room.entity.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReadLaterViewModel(focusDao: FocusDao) : ViewModel() {

    var focusRepository: FocusRepositoryImpl = FocusRepositoryImpl.getInstance(focusDao)

    private var allReadLaterNews = MutableLiveData<List<News>>()

    fun getAllReadLaterNews(): LiveData<List<News>> = allReadLaterNews

    fun fetchReadLaterNews() {

        viewModelScope.launch(Dispatchers.IO) {

            allReadLaterNews.postValue(focusRepository.getAllReadLaterNews())
        }
    }

    fun removeFromReadLater(news: News) {
        viewModelScope.launch(Dispatchers.IO) {
            focusRepository.removeNewsFromReadLater(news)
            fetchReadLaterNews()
        }
    }

}