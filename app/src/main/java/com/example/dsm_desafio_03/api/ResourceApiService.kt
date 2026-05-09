package com.example.dsm_desafio_03.api

import com.example.dsm_desafio_03.model.ApiDetailResponse
import com.example.dsm_desafio_03.model.ApiResponse
import com.example.dsm_desafio_03.model.AddFavoriteRequest
import com.example.dsm_desafio_03.model.AddFavoriteResponse
import com.example.dsm_desafio_03.model.ApiFavoritesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ResourceApiService {
    @GET("api/resources/getResources")
    suspend fun getResources(
        @Query("type") type: String? = null,
        @Query("search") search: String? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null
    ): ApiResponse

    @GET("api/resources/getResource/{id}")
    suspend fun getResourceDetail(
        @Path("id") id: Int
    ): ApiDetailResponse

    @POST("api/resources/toggleFavoriteResource")
    suspend fun addFavoriteResource(
        @Header("x-token") token: String,
        @Body request: AddFavoriteRequest
    ): Response<AddFavoriteResponse>

    @GET("api/resources/getFavoritesResourcesByUser/{userId}")
    suspend fun getFavoritesResourcesByUser(
        @Path("userId") userId: Int
    ): ApiFavoritesResponse

    @POST("api/resources/rateResource")
    suspend fun rateResource(
        @Header("x-token") token: String,
        @Body request: com.example.dsm_desafio_03.model.RatingRequest
    ): Response<com.example.dsm_desafio_03.model.RatingResponse>

    @PUT("api/resources/updateResource/{id}")
    suspend fun updateResource(
        @Header("x-token") token: String,
        @Path("id") id: Int,
        @Body request: com.example.dsm_desafio_03.model.UpdateResourceRequest
    ): Response<com.example.dsm_desafio_03.model.UpdateResourceResponse>

    @POST("api/resources/createResource")
    suspend fun createResource(
        @Header("x-token") token: String,
        @Body request: com.example.dsm_desafio_03.model.UpdateResourceRequest
    ): Response<com.example.dsm_desafio_03.model.UpdateResourceResponse>

    @DELETE("api/resources/deleteResource/{id}")
    suspend fun deleteResource(
        @Header("x-token") token: String,
        @Path("id") id: Int
    ): Response<com.example.dsm_desafio_03.model.UpdateResourceResponse>
}