package com.app.focusindepth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.focusindepth.repository.FocusRepositoryImpl
import com.app.focusindepth.room.dao.FocusDao
import com.app.focusindepth.room.entity.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsCategoriesViewModel(focusDao: FocusDao) : ViewModel() {

    var focusRepository: FocusRepositoryImpl = FocusRepositoryImpl.getInstance(focusDao)

    private var allCategories = MutableLiveData<List<Category>>()

    fun getAllCategories(): LiveData<List<Category>> = allCategories

    init {

        viewModelScope.launch(Dispatchers.IO) {

            var categoriesList = focusRepository.getAllCategories()

            if (categoriesList.isEmpty()) {

                focusRepository.addAllCategories()
                categoriesList = focusRepository.getAllCategories()

            }

            allCategories.postValue(categoriesList)
        }
    }

}