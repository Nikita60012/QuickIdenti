package com.example.quickidenti.api

import com.example.quickidenti.dto.client.response.CheckSubscribe
import com.example.quickidenti.dto.client.response.GetIdentification
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Identification {
    @POST("/identification_people/identification/{client_token}")
    suspend fun identification(
        @Path("client_token") clientToken: String,
        @Body photo: String
    ): Boolean

    @GET("/identification_people/identification_get/{client_token}/{identification_id}")
    suspend fun getIdentification(
        @Path("client_token") clientToken: String,
        @Path("identification_id") identificationId: Int
    ): GetIdentification

    @GET("client/client_check_subscribe/{client_token}")
    suspend fun checkSubscribe(@Path("client_token") clientToken: String): CheckSubscribe
}