package com.ayberk.ordercoffee.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.ordercoffee.BasketRepository
import com.ayberk.ordercoffee.presentation.model.BasketProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val basketRepository: BasketRepository
) : ViewModel() {

    private val _basketItems = MutableLiveData<List<BasketProduct>>()
    val basketItems: LiveData<List<BasketProduct>> = _basketItems

    // Sepete ürün ekleme fonksiyonu
    fun addProductToBasket(product: BasketProduct, onAdded: (BasketProduct) -> Unit, onAlreadyExists: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            // Sepette ürün var mı kontrol et
            if (basketRepository.getAllBasketProducts().none { it.id == product.id }) {
                // Eğer yoksa ekle
                basketRepository.insertIfNotExists(product)
                withContext(Dispatchers.Main) {
                    onAdded(product)
                }
            } else {
                withContext(Dispatchers.Main) {
                    onAlreadyExists()
                }
            }
        }
    }

    fun deleteProductById(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            basketRepository.deleteProductById(productId)
            getBasketItems()
        }
    }


    // Sepetteki tüm ürünleri al
    fun getBasketItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = basketRepository.getAllBasketProducts() // Room'dan veri al
            withContext(Dispatchers.Main) {
                _basketItems.value = items // Veriyi LiveData'ya aktar
            }
        }
    }
}
