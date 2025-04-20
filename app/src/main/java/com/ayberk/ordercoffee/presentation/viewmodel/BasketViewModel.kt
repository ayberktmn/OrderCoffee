package com.ayberk.ordercoffee.presentation.viewmodel

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

    fun addProductToBasket(product: BasketProduct, onAdded: (BasketProduct) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            basketRepository.insertProduct(product)

            withContext(Dispatchers.Main) {
                onAdded(product)
            }
        }
    }
}
