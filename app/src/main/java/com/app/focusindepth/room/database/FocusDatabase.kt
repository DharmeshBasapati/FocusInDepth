package com.app.focusindepth.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.focusindepth.room.dao.FocusDao
import com.app.focusindepth.room.entity.Category
import com.app.focusindepth.room.entity.News

@Database(entities = [Category::class,News::class], version = 1)
abstract class FocusDatabase: RoomDatabase() {

    abstract fun focusDao() : FocusDao

}