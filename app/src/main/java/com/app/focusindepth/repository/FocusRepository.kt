package com.app.focusindepth.repository

import androidx.lifecycle.MutableLiveData
import com.app.focusindepth.models.News
import com.app.focusindepth.models.NewsResponse
import com.app.focusindepth.room.entity.Category
import com.app.focusindepth.utils.Resource

interface FocusRepository {

    suspend fun addAllCategories()

    suspend fun getAllCategories(): List<Category>

    fun getNewsFromCategory(categoryName: String): MutableLiveData<Resource<List<News>>>

}