package com.app.focusindepth.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.focusindepth.R
import com.app.focusindepth.models.NewsResponse
import com.app.focusindepth.network.RetrofitBuilder
import com.app.focusindepth.room.dao.FocusDao
import com.app.focusindepth.room.entity.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object FocusRepositoryImpl: FocusRepository {

    private val TAG = "FocusRepositoryImpl"

    private var focusInstance: FocusRepositoryImpl? = null

    private lateinit var focusDao : FocusDao

    fun getInstance(_focusDao: FocusDao): FocusRepositoryImpl {
        if (focusInstance == null) {
            focusInstance = FocusRepositoryImpl
        }
        focusDao = _focusDao
        return focusInstance!!
    }

    override suspend fun addAllCategories() {

        val categoriesList = ArrayList<Category>()

        categoriesList.add(Category(categoryName = "National",categoryImage = R.drawable.ic_news_categories))
        categoriesList.add(Category(categoryName = "Business",categoryImage = R.drawable.ic_news_categories))
        categoriesList.add(Category(categoryName = "Sports",categoryImage = R.drawable.ic_news_categories))
        categoriesList.add(Category(categoryName = "World",categoryImage = R.drawable.ic_news_categories))
        categoriesList.add(Category(categoryName = "Politics",categoryImage = R.drawable.ic_news_categories))
        categoriesList.add(Category(categoryName = "Technology",categoryImage = R.drawable.ic_news_categories))
        categoriesList.add(Category(categoryName = "Startup",categoryImage = R.drawable.ic_news_categories))
        categoriesList.add(Category(categoryName = "Entertainment",categoryImage = R.drawable.ic_news_categories))
        categoriesList.add(Category(categoryName = "Miscellaneous",categoryImage = R.drawable.ic_news_categories))
        categoriesList.add(Category(categoryName = "Hatke",categoryImage = R.drawable.ic_news_categories))
        categoriesList.add(Category(categoryName = "Science",categoryImage = R.drawable.ic_news_categories))
        categoriesList.add(Category(categoryName = "Automobile",categoryImage = R.drawable.ic_news_categories))

        focusDao.insertAllCategories(categoriesList)

    }

    override suspend fun getAllCategories(): List<Category> {
        return focusDao.getAllCategories()
    }

    override suspend fun getNewsFromCategory(categoryName: String): MutableLiveData<NewsResponse> {

        val newsResponse = MutableLiveData<NewsResponse>()

        RetrofitBuilder.apiServices.getNewsForThisCategory(categoryName).enqueue(object :
            Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                newsResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })

        return newsResponse
    }

}