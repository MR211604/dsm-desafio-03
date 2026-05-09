package com.example.dsm_desafio_03.utils

import android.util.Base64
import org.json.JSONObject

object JwtUtils {
    fun getUserIdFromToken(token: String?): Int? {
        if (token == null) return null
        try {
            val parts = token.split(".")
            if (parts.size == 3) {
                val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
                val jsonObject = JSONObject(payload)
                val userIdStr = jsonObject.optString("id", "")
                if (userIdStr.isNotEmpty()) {
                    return userIdStr.toInt()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getUserRolFromToken(token: String?): String? {
        if (token == null) return null
        try {
            val parts = token.split(".")
            if (parts.size == 3) {
                val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
                val jsonObject = JSONObject(payload)
                return jsonObject.optString("rol", null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}

