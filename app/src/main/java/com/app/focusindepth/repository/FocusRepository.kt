package com.app.focusindepth.repository

import androidx.lifecycle.MutableLiveData
import com.app.focusindepth.models.NewsResponse
import com.app.focusindepth.room.entity.Category

interface FocusRepository {

    suspend fun addAllCategories()

    suspend fun getAllCategories(): List<Category>

    suspend fun getNewsFromCategory(categoryName: String): MutableLiveData<NewsResponse>

}