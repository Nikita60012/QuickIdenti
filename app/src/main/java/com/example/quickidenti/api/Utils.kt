package com.example.quickidenti.api

import retrofit2.http.GET

interface Utils {
    @GET("/utils/check_connection")
    suspend fun check(): Boolean
}