package com.app.focusindepth.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.focusindepth.R
import com.app.focusindepth.models.News
import com.app.focusindepth.models.NewsResponse
import com.app.focusindepth.network.RetrofitBuilder
import com.app.focusindepth.room.dao.FocusDao
import com.app.focusindepth.room.entity.Category
import com.app.focusindepth.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object FocusRepositoryImpl : FocusRepository {

    private var focusInstance: FocusRepositoryImpl? = null

    private lateinit var focusDao: FocusDao

    var newsResponse = MutableLiveData<Resource<List<News>>>()

    fun getInstance(_focusDao: FocusDao): FocusRepositoryImpl {
        if (focusInstance == null) {
            focusInstance = FocusRepositoryImpl
        }
        focusDao = _focusDao
        newsResponse = MutableLiveData()
        return focusInstance!!
    }

    override suspend fun addAllCategories() {

        val categoriesList = ArrayList<Category>()

        categoriesList.add(
            Category(
                categoryName = "National",
                categoryImage = R.drawable.ic_national
            )
        )
        categoriesList.add(
            Category(
                categoryName = "Business",
                categoryImage = R.drawable.ic_business
            )
        )
        categoriesList.add(
            Category(
                categoryName = "Sports",
                categoryImage = R.drawable.ic_sports
            )
        )
        categoriesList.add(
            Category(
                categoryName = "World",
                categoryImage = R.drawable.ic_world
            )
        )
        categoriesList.add(
            Category(
                categoryName = "Politics",
                categoryImage = R.drawable.ic_politics
            )
        )
        categoriesList.add(
            Category(
                categoryName = "Technology",
                categoryImage = R.drawable.ic_technology
            )
        )
        categoriesList.add(
            Category(
                categoryName = "Startup",
                categoryImage = R.drawable.ic_startup
            )
        )
        categoriesList.add(
            Category(
                categoryName = "Entertainment",
                categoryImage = R.drawable.ic_entertainment
            )
        )
        categoriesList.add(
            Category(
                categoryName = "Miscellaneous",
                categoryImage = R.drawable.ic_miscellaneous
            )
        )
        categoriesList.add(
            Category(
                categoryName = "Hatke",
                categoryImage = R.drawable.ic_hatke
            )
        )
        categoriesList.add(
            Category(
                categoryName = "Science",
                categoryImage = R.drawable.ic_science
            )
        )
        categoriesList.add(
            Category(
                categoryName = "Automobile",
                categoryImage = R.drawable.ic_automobile
            )
        )

        focusDao.insertAllCategories(categoriesList)

    }

    override suspend fun getAllCategories(): List<Category> {
        return focusDao.getAllCategories()
    }

    override fun getNewsFromCategory(categoryName: String): MutableLiveData<Resource<List<News>>> {

        newsResponse.postValue(Resource.loading(null))

        RetrofitBuilder.apiServices.getNewsForThisCategory(categoryName).enqueue(object :
            Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                newsResponse.value = Resource.success(response.body()?.data)
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                newsResponse.postValue(Resource.error(null, t.message))
            }
        })

        return newsResponse
    }

    fun getNewsResponse(): LiveData<Resource<List<News>>> = newsResponse

}