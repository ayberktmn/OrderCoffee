package com.ayberk.ordercoffee.presentation.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_table")
data class BasketProduct (
    @PrimaryKey val id: Int, // Ürün ID'si
    val name: String,       // Ürün ismi
    val price: Double,      // Ürün fiyatı
    val imageUrl: Int,      // Ürün görseli (drawable id olarak tutuluyor)
    val categoryName: String,  // Ürün kategorisi
    var quantity: Int = 1   // Sepetteki adet
){
    fun increaseQuantity() {
        quantity++
    }

    fun decreaseQuantity() {
        if (quantity > 1) { // Miktar 1'den küçük olmasın
            quantity--
        }
    }

    // Toplam fiyatı hesapla
    fun getTotalPrice(): Double {
        return price * quantity
    }
}