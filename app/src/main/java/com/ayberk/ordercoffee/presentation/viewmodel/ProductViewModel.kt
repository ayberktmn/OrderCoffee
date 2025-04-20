package com.ayberk.ordercoffee.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayberk.ordercoffee.R
import com.ayberk.ordercoffee.presentation.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor() : ViewModel() {

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> get() = _productList

    private val allProducts = listOf(
        Product(1, "Latte", 99.00, R.drawable.latte, "Sıcak İçecekler"),
        Product(2, "Cappuccino", 120.00, R.drawable.cup, "Soğuk İçecekler"),
        Product(3, "Americano", 90.00, R.drawable.kahveee, "Sıcak İçecekler"),
        Product(4, "Espresso", 110.00, R.drawable.turkkahvesi, "Soğuk İçecekler"),
        Product(5, "Filtre Kahve", 70.00, R.drawable.kahveler, "Sıcak İçecekler"),
        Product(6, "Soğuk Kahve", 80.00, R.drawable.kahveee, "Soğuk İçecekler"),
        Product(7, "Pizza", 150.00, R.drawable.pizza, "Yiyecekler")
    )

    init {
        // Fragment açıldığında tüm ürünler sıralanıyor
        _productList.value = allProducts
    }

    fun filterProductsByCategory(category: String) {
        if (category == "Tümü") {
            _productList.value = allProducts
        } else {
            _productList.value = allProducts.filter { it.categoryName == category }
        }
    }
}

