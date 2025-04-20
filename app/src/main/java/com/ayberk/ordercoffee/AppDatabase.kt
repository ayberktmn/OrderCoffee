package com.ayberk.ordercoffee

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ayberk.ordercoffee.room.FavoriteDao
import com.ayberk.ordercoffee.room.FavoriteProduct

@Database(entities = [FavoriteProduct::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
