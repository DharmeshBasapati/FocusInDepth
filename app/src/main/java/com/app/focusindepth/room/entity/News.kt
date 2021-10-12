package com.app.focusindepth.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "news", foreignKeys = [ForeignKey(
    entity = Category::class,
    parentColumns = arrayOf("categoryId"),
    childColumns = arrayOf("categoryId")
)])
data class News(
    @PrimaryKey(autoGenerate = true) val newsId: Int = 0,
    val categoryId: Int,
    val author: String,
    val content: String,
    val date: String,
    val imageUrl: String,
    val readMoreUrl: String,
    val time: String,
    val title: String,
    val url: String,
    val isReadLater: Boolean
)
