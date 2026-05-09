package com.example.dsm_desafio_03.api

import com.example.dsm_desafio_03.model.LoginRequest
import com.example.dsm_desafio_03.model.LoginResponse
import com.example.dsm_desafio_03.model.RegisterRequest
import com.example.dsm_desafio_03.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}
