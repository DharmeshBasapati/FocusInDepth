package com.app.focusindepth.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true) var categoryId: Int = 0,
    val categoryName: String,
    val categoryImage: Int

)