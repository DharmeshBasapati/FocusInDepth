package com.app.focusindepth.room.dao

import androidx.room.*
import com.app.focusindepth.room.entity.Category
import com.app.focusindepth.room.entity.News

@Dao
interface FocusDao {

    @Insert
    fun insertAllCategories(categoriesList: List<Category>)

    @Query("SELECT * FROM category")
    fun getAllCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewsToReadLater(news: News)

    @Query("SELECT * FROM news")
    fun getAllReadLaterNews(): List<News>

    @Delete
    fun deleteNewsFromReadLater(news: News)

}