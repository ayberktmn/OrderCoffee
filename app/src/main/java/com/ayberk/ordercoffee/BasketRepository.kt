package com.ayberk.ordercoffee

import androidx.lifecycle.LiveData
import com.ayberk.ordercoffee.data.local.dao.BasketDao
import com.ayberk.ordercoffee.presentation.model.BasketProduct
import javax.inject.Inject

class BasketRepository @Inject constructor(private val basketDao: BasketDao) {

    suspend fun getAllBasketItems(): List<BasketProduct> {
        return basketDao.getAllBasketItems()
    }

    suspend fun insertProduct(product: BasketProduct) {
        basketDao.insertToBasket(product)
    }

}
