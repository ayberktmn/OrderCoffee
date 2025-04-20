package com.ayberk.ordercoffee

import androidx.lifecycle.LiveData
import com.ayberk.ordercoffee.data.local.dao.BasketDao
import com.ayberk.ordercoffee.presentation.model.BasketProduct
import javax.inject.Inject


class BasketRepository @Inject constructor(private val basketDao: BasketDao) {

    suspend fun insertIfNotExists(basketProduct: BasketProduct) {
        if (!basketDao.exists(basketProduct.id)) {
            basketDao.insertToBasket(basketProduct)
        }
    }

    suspend fun getAllBasketProducts(): List<BasketProduct> {
        return basketDao.getAllBasketItems()
    }

    suspend fun deleteProductById(productId: Int) {
        basketDao.deleteById(productId)
    }

    suspend fun exists(id: Int): Boolean {
        return basketDao.exists(id)
    }
}

