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

    init {
        fetchProducts()
    }

    private fun fetchProducts() {

        val products = listOf(
            Product(1, "Latte", 99.99, R.drawable.latte),
            Product(2, "Cappuccino", 120.50, R.drawable.cup),
            Product(3, "Americano", 90.00, R.drawable.kahveee),
            Product(4, "Espresso", 110.00, R.drawable.turkkahvesi),
            Product(5, "Filtre Kahve", 70.00, R.drawable.kahveler)
        )
        _productList.value = products
    }
}
