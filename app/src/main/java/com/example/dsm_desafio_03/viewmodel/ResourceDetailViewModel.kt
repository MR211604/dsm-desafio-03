package com.example.dsm_desafio_03.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsm_desafio_03.model.ResourceModel
import com.example.dsm_desafio_03.model.ResourceRepository
import com.example.dsm_desafio_03.model.AddFavoriteRequest
import kotlinx.coroutines.launch

class ResourceDetailViewModel : ViewModel() {
    private val repository = ResourceRepository()

    private val _resource = MutableLiveData<ResourceModel?>()
    val resource: LiveData<ResourceModel?> = _resource

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchResourceDetail(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.getResourceDetail(id)
                if (response.ok) {
                    _resource.value = response.data
                } else {
                    _error.value = response.message
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addFavoriteResource(token: String, userId: Int, resourceId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = AddFavoriteRequest(userId, resourceId)
                val response = repository.addFavoriteResource(token, request)
                if (response.isSuccessful && response.body()?.ok == true) {
                    _error.value = response.body()?.message ?: "Added to favorites successfully"
                } else {
                    _error.value = response.body()?.message ?: "Failed to add to favorites"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun rateResource(token: String, userId: Int, resourceId: Int, rating: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = com.example.dsm_desafio_03.model.RatingRequest(userId, resourceId, rating)
                val response = repository.rateResource(token, request)
                if (response.isSuccessful && response.body()?.ok == true) {
                    _error.value = response.body()?.message ?: "Rating submitted successfully"
                    fetchResourceDetail(resourceId)
                } else {
                    _error.value = response.body()?.message ?: "Failed to submit rating"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
