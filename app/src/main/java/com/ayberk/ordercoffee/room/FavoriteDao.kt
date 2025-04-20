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

    @Query("SELECT EXISTS(SELECT 1 FROM FavoriteProduct WHERE id = :id)")
    fun exists(id: Int): Boolean

    // Ürünü yalnızca veritabanında yoksa ekleyin
    suspend fun insertIfNotExists(favoriteProduct: FavoriteProduct) {
        if (!exists(favoriteProduct.id)) {
            insert(favoriteProduct)
        }
    }
}

