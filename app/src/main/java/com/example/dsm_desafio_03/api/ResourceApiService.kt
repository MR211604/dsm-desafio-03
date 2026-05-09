package com.example.dsm_desafio_03.api

import com.example.dsm_desafio_03.model.ApiDetailResponse
import com.example.dsm_desafio_03.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ResourceApiService {
    @GET("api/resources/getResources")
    suspend fun getResources(
        @Query("type") type: String? = null,
        @Query("search") search: String? = null
    ): ApiResponse

    @GET("api/resources/getResource/{id}")
    suspend fun getResourceDetail(
        @Path("id") id: Int
    ): ApiDetailResponse
}