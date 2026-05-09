package com.example.dsm_desafio_03.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsm_desafio_03.model.Pagination
import com.example.dsm_desafio_03.model.ResourceModel
import com.example.dsm_desafio_03.model.ResourceRepository
import com.example.dsm_desafio_03.model.UpdateResourceRequest
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val repository = ResourceRepository()

    private val _resources = MutableLiveData<List<ResourceModel>>()
    val resources: LiveData<List<ResourceModel>> = _resources

    private val _pagination = MutableLiveData<Pagination>()
    val pagination: LiveData<Pagination> = _pagination

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _updateSuccess = MutableLiveData<String?>()
    val updateSuccess: LiveData<String?> = _updateSuccess

    fun clearSuccessMessage() {
        _updateSuccess.value = null
    }

    private var currentType: String? = null
    private var currentSearch: String? = null
    private var currentPage: Int = 1
    private val pageLimit: Int = 10

    fun fetchResources(
        type: String? = currentType,
        search: String? = currentSearch,
        page: Int = currentPage
    ) {
        currentType = type
        currentSearch = search
        currentPage = page
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val typeParam = if (type == "ALL" || type.isNullOrEmpty()) null else type
                val response = repository.getResources(typeParam, search, page, pageLimit)
                if (response.ok) {
                    _resources.value = response.data
                    _pagination.value = response.pagination
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

    fun nextPage() {
        val pag = _pagination.value ?: return
        if (currentPage < pag.totalPages) {
            fetchResources(page = currentPage + 1)
        }
    }

    fun previousPage() {
        if (currentPage > 1) {
            fetchResources(page = currentPage - 1)
        }
    }

    fun updateResource(token: String, id: Int, request: UpdateResourceRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _updateSuccess.value = null
            try {
                val response = repository.updateResource(token, id, request)
                if (response.isSuccessful && response.body()?.ok == true) {
                    _updateSuccess.value = response.body()?.message ?: "Resource updated successfully"
                    fetchResources() // Refresh list
                } else {
                    _error.value = response.body()?.message ?: "Error updating resource"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createResource(token: String, request: UpdateResourceRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _updateSuccess.value = null
            try {
                val response = repository.createResource(token, request)
                if (response.isSuccessful && response.body()?.ok == true) {
                    _updateSuccess.value = response.body()?.message ?: "Resource created successfully"
                    fetchResources() // Refresh list
                } else {
                    _error.value = response.body()?.message ?: "Error creating resource"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteResource(token: String, id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _updateSuccess.value = null
            try {
                val response = repository.deleteResource(token, id)
                if (response.isSuccessful && response.body()?.ok == true) {
                    _updateSuccess.value = response.body()?.message ?: "Resource deleted successfully"
                    fetchResources() // Refresh list
                } else {
                    _error.value = response.body()?.message ?: "Error deleting resource"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
