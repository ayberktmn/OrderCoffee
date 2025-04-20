package com.ayberk.ordercoffee.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.ordercoffee.room.FavoriteDao
import com.ayberk.ordercoffee.presentation.model.FavoriteProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteDao: FavoriteDao) : ViewModel() {

    private val _favoriteProducts = MutableLiveData<List<FavoriteProduct>>()
    val favoriteProducts: LiveData<List<FavoriteProduct>> get() = _favoriteProducts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    // Add favorite product to database
    fun addFavoriteProduct(product: FavoriteProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteDao.insertIfNotExists(product)
            _isFavorite.postValue(true) // Update the state to true
            loadFavoriteProducts()  // Reload the favorite products
        }
    }

    fun checkIfFavorite(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = favoriteDao.exists(productId)
            _isFavorite.postValue(isFavorite) // Update the state based on the query result
        }
    }

    // Check if product is already a favorite
    suspend fun isFavorite(productId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            favoriteDao.exists(productId)
        }
    }

    // Remove favorite product from database
    fun deleteFavoriteProduct(product: FavoriteProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteDao.delete(product)
            _isFavorite.postValue(false) // Update the state to false
            loadFavoriteProducts()  // Reload the favorite products
        }
    }

    // Load all favorite products
    fun loadFavoriteProducts() {
        _isLoading.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            val data = favoriteDao.getAll()
            _favoriteProducts.postValue(data)
            _isLoading.postValue(false)
        }
    }
}

