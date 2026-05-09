package com.example.dsm_desafio_03.model

import com.example.dsm_desafio_03.api.ApiClient

class ResourceRepository {
    private val api = ApiClient.resourceApi

    suspend fun getResources(type: String? = null, search: String? = null, page: Int? = null, limit: Int? = null): ApiResponse {
        return api.getResources(type, search, page, limit)
    }

    suspend fun getResourceDetail(id: Int): ApiDetailResponse {
        return api.getResourceDetail(id)
    }

    suspend fun addFavoriteResource(token: String, request: AddFavoriteRequest): retrofit2.Response<AddFavoriteResponse> {
        return api.addFavoriteResource(token, request)
    }

    suspend fun getFavoritesResourcesByUser(userId: Int): ApiFavoritesResponse {
        return api.getFavoritesResourcesByUser(userId)
    }

    suspend fun updateResource(token: String, id: Int, request: UpdateResourceRequest): retrofit2.Response<UpdateResourceResponse> {
        return api.updateResource(token, id, request)
    }

    suspend fun createResource(token: String, request: UpdateResourceRequest): retrofit2.Response<UpdateResourceResponse> {
        return api.createResource(token, request)
    }

    suspend fun deleteResource(token: String, id: Int): retrofit2.Response<UpdateResourceResponse> {
        return api.deleteResource(token, id)
    }

    suspend fun rateResource(token: String, request: RatingRequest): retrofit2.Response<RatingResponse> {
        return api.rateResource(token, request)
    }
}
