package com.example.dsm_desafio_03.model

data class ResourceModel(
    val id: Int,
    val title: String,
    val description: String,
    val url: String,
    val type: String,
    val image: String?,
    val createdAt: String,
    val updatedAt: String,
    val userRatings: List<UserRating>? = null
)

data class UserRating(
    val id: Int,
    val userId: Int,
    val resourceId: Int,
    val rating: Int,
    val createdAt: String,
    val updatedAt: String
)

data class Pagination(
    val total: Int,
    val page: Int,
    val limit: Int,
    val totalPages: Int,
    val count: Int
)

data class ApiResponse(
    val ok: Boolean,
    val message: String,
    val data: List<ResourceModel>,
    val pagination: Pagination
)

data class ApiDetailResponse(
    val ok: Boolean,
    val message: String,
    val data: ResourceModel
)

data class AddFavoriteRequest(
    val userId: Int,
    val resourceId: Int
)

data class AddFavoriteResponse(
    val ok: Boolean,
    val message: String
)

data class FavoriteResource(
    val id: Int,
    val userId: Int,
    val resourceId: Int,
    val createdAt: String,
    val updatedAt: String,
    val resource: ResourceModel
)

data class UpdateResourceRequest(
    val title: String,
    val description: String,
    val url: String,
    val image: String,
    val type: String
)

data class UpdateResourceResponse(
    val ok: Boolean,
    val message: String
    // We can omit data if not strictly needed, or add it as `val data: ResourceModel?`
)

data class ApiFavoritesResponse(
    val ok: Boolean,
    val message: String,
    val data: List<FavoriteResource>
)

data class RatingRequest(
    val userId: Int,
    val resourceId: Int,
    val rating: Int
)

data class RatingResponse(
    val ok: Boolean,
    val message: String
)
