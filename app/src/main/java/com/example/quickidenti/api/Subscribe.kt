package com.example.quickidenti.api

import com.example.quickidenti.dto.subscribe.SubscribeBuy
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface Subscribe {
    @POST("/subscribes/buy_subscribe/{client_email}")
    suspend fun buySubscribe(@Path("client_email") client_email: String,
                             @Body data: SubscribeBuy): Boolean


}