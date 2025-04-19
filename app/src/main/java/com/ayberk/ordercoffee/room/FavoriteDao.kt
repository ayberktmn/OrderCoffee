package com.ayberk.ordercoffee.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FavoriteProduct")
    fun getAll(): List<FavoriteProduct>

    @Insert
    fun insert(favoriteProduct: FavoriteProduct)

    @Delete
    fun delete(favoriteProduct: FavoriteProduct)

    @Query("SELECT * FROM FavoriteProduct WHERE id = :id")
    fun getById(id: Int): FavoriteProduct?

}
