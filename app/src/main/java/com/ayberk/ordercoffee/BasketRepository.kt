package com.ayberk.ordercoffee

import com.ayberk.ordercoffee.data.local.dao.BasketDao
import com.ayberk.ordercoffee.presentation.model.BasketProduct
import javax.inject.Inject

class BasketRepository @Inject constructor(private val basketDao: BasketDao) {

    suspend fun insertIfNotExists(basketProduct: BasketProduct) {
        if (!basketDao.exists(basketProduct.id)) {
            basketDao.insertToBasket(basketProduct)
        } else {
            basketDao.updateBasketProduct(basketProduct) // Var olan ürünü güncelle
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

    suspend fun updateProductInDb(basketProduct: BasketProduct) {
        basketDao.updateBasketProduct(basketProduct)
    }

    // Yeni ürün güncelleme fonksiyonu
    suspend fun updateProduct(basketProduct: BasketProduct) {
        // Veritabanındaki ürünü güncelle
        basketDao.updateBasketProduct(basketProduct)
    }
}



