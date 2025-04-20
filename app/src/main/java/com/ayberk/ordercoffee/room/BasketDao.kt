package com.ayberk.ordercoffee.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ayberk.ordercoffee.presentation.model.BasketProduct
import com.ayberk.ordercoffee.presentation.model.FavoriteProduct

@Dao
interface BasketDao {

    @Insert
    fun insertToBasket(basketProduct: BasketProduct)

    @Query("SELECT * FROM basket_table")
    fun getAllBasketItems(): List<BasketProduct>

}
