package com.ayberk.ordercoffee.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.ordercoffee.room.FavoriteDao
import com.ayberk.ordercoffee.room.FavoriteProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteDao: FavoriteDao) : ViewModel() {

    private val _favoriteProducts = MutableLiveData<List<FavoriteProduct>>()
    val favoriteProducts: LiveData<List<FavoriteProduct>> get() = _favoriteProducts

    // Favori ürünü veritabanına eklemek
    fun addFavoriteProduct(product: FavoriteProduct) {
        val favoriteProduct = FavoriteProduct(
            name = product.name,
            price = product.price,
            imageUrl = product.imageUrl,
            category = product.category
        )

        // Veritabanı işlemini arka planda yapıyoruz (viewModelScope içinde)
        viewModelScope.launch(Dispatchers.IO) { // IO dispatcher kullanıyoruz
            favoriteDao.insert(favoriteProduct)
            loadFavoriteProducts()  // Favori ürünleri yüklemek
        }
    }

    // Favori ürünleri yüklemek
    fun loadFavoriteProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteProducts.postValue(favoriteDao.getAll())
        }
    }
}
