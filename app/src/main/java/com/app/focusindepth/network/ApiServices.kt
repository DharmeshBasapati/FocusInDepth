package com.app.focusindepth.network

import com.app.focusindepth.models.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("news")
    fun getNewsForThisCategory(@Query("category") categoryName: String): Call<NewsResponse>

}