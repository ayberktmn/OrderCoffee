package com.ayberk.ordercoffee.data.local.dao

import androidx.room.*
import com.ayberk.ordercoffee.presentation.model.BasketProduct

@Dao
interface BasketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Eğer aynı ID'li ürün varsa, mevcut ürünü güncelle
    fun insertToBasket(basketProduct: BasketProduct)

    @Update
    fun updateBasketProduct(basketProduct: BasketProduct): Int  // Güncellenen satır sayısını döndürür

    @Query("SELECT * FROM basket_table")
    fun getAllBasketItems(): List<BasketProduct>

    @Query("SELECT * FROM basket_table WHERE id = :productId LIMIT 1")
    fun getProductById(productId: Int): BasketProduct?

    @Query("SELECT EXISTS(SELECT 1 FROM basket_table WHERE id = :id)")
    fun exists(id: Int): Boolean

    @Query("DELETE FROM basket_table WHERE id = :productId")
    fun deleteById(productId: Int)

    // Eğer ürün yoksa ekleyin, varsa güncelleyin
    suspend fun insertIfNotExists(basketProduct: BasketProduct) {
        if (!exists(basketProduct.id)) {
            insertToBasket(basketProduct)
        } else {
            updateBasketProduct(basketProduct)
        }
    }
}
