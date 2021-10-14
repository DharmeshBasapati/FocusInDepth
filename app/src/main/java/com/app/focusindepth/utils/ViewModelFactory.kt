package com.app.focusindepth.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.focusindepth.room.dao.FocusDao
import com.app.focusindepth.viewmodels.NewsCategoriesViewModel
import com.app.focusindepth.viewmodels.NewsViewModel
import com.app.focusindepth.viewmodels.ReadLaterViewModel

class ViewModelFactory(private val focusDao: FocusDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsCategoriesViewModel::class.java)){
            return NewsCategoriesViewModel(focusDao) as T
        }
        if(modelClass.isAssignableFrom(ReadLaterViewModel::class.java)){
            return ReadLaterViewModel(focusDao) as T
        }
        if(modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(focusDao) as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }
}