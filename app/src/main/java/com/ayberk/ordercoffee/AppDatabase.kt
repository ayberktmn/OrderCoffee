package com.ayberk.ordercoffee

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ayberk.ordercoffee.data.local.dao.BasketDao
import com.ayberk.ordercoffee.room.FavoriteDao
import com.ayberk.ordercoffee.presentation.model.BasketProduct
import com.ayberk.ordercoffee.presentation.model.FavoriteProduct

@Database(entities = [FavoriteProduct::class, BasketProduct::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    // Favorite ürünler için DAO
    abstract fun favoriteDao(): FavoriteDao

    // Basket ürünler için DAO
    abstract fun basketDao(): BasketDao
}

