package com.example.dsm_desafio_03.model

import com.example.dsm_desafio_03.api.ApiClient

class ResourceRepository {
    private val api = ApiClient.resourceApi

    suspend fun getResources(type: String? = null, search: String? = null): ApiResponse {
        return api.getResources(type, search)
    }

    suspend fun getResourceDetail(id: Int): ApiDetailResponse {
        return api.getResourceDetail(id)
    }
}
