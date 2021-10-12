package com.app.focusindepth.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.focusindepth.room.entity.Category

@Dao
interface FocusDao {

    @Insert
    fun insertAllCategories(categoriesList: List<Category>)

    @Query("SELECT * FROM category")
    fun getAllCategories(): List<Category>

}