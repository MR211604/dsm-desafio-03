package com.example.dsm_desafio_03.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsm_desafio_03.model.ResourceModel
import com.example.dsm_desafio_03.model.ResourceRepository
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
}
