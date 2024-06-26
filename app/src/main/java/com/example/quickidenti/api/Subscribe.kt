package com.example.quickidenti.api

import com.example.quickidenti.dto.subscribe.SubscribeBuy
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface Subscribe {
    @POST("/subscribe/buy_subscribe/{client_token}")
    suspend fun buySubscribe(
        @Path("client_token") clientToken: String,
        @Body data: SubscribeBuy
    ): Boolean
}