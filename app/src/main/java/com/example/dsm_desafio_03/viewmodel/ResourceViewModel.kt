package com.example.dsm_desafio_03.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsm_desafio_03.model.ResourceModel
import com.example.dsm_desafio_03.model.ResourceRepository
import kotlinx.coroutines.launch

class ResourceViewModel : ViewModel() {
    private val repository = ResourceRepository()

    private val _resources = MutableLiveData<List<ResourceModel>>()
    val resources: LiveData<List<ResourceModel>> = _resources

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var currentType: String? = null
    private var currentSearch: String? = null

    fun fetchResources(type: String? = currentType, search: String? = currentSearch) {
        currentType = type
        currentSearch = search
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                // When type is "ALL", passing null to get everything
                val typeParam = if (type == "ALL" || type.isNullOrEmpty()) null else type
                val response = repository.getResources(typeParam, search)
                if (response.ok) {
                    _resources.value = response.data
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
