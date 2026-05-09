package com.example.dsm_desafio_03.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsm_desafio_03.model.FavoriteResource
import com.example.dsm_desafio_03.model.ResourceRepository
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {
    private val repository = ResourceRepository()

    private val _favorites = MutableLiveData<List<FavoriteResource>>()
    val favorites: LiveData<List<FavoriteResource>> get() = _favorites

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchFavorites(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.getFavoritesResourcesByUser(userId)
                if (response.ok) {
                    _favorites.value = response.data
                } else {
                    _error.value = response.message
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

