package com.example.dsm_desafio_03.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val ok: Boolean,
    val message: String,
    val token: String? = null
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val userRol: Int
)

data class UserDto(
    val id: Int,
    val username: String,
    val email: String,
    val photoUrl: String?,
    val createdAt: String,
    val updatedAt: String,
    val userRolId: Int
)

data class RegisterResponse(
    val ok: Boolean,
    val message: String,
    val user: UserDto? = null
)
