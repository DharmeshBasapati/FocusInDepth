package com.app.focusindepth.models

import java.io.Serializable

data class NewsResponse(
    val category: String,
    val `data`: List<News>,
    val success: Boolean
)

data class News(
    val author: String,
    val content: String,
    val date: String,
    val imageUrl: String,
    val readMoreUrl: String,
    val time: String,
    val title: String,
    val url: String
): Serializable