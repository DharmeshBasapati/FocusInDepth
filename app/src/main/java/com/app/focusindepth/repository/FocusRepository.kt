package com.app.focusindepth.repository

import androidx.lifecycle.MutableLiveData
import com.app.focusindepth.models.News as NEWS
import com.app.focusindepth.models.NewsResponse
import com.app.focusindepth.room.entity.Category
import com.app.focusindepth.room.entity.News as READ_LATER
import com.app.focusindepth.utils.Resource

interface FocusRepository {

    suspend fun addAllCategories()

    suspend fun getAllCategories(): List<Category>

    fun getNewsFromCategory(categoryName: String): MutableLiveData<Resource<List<NEWS>>>

    suspend fun addNewsToReadLater(news: NEWS)

    suspend fun getAllReadLaterNews(): List<READ_LATER>

    suspend fun removeNewsFromReadLater(news: READ_LATER)
}