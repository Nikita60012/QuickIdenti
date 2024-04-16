package com.example.quickidenti.api

import com.example.quickidenti.dto.client.ClientChangeData
import com.example.quickidenti.dto.client.ClientReg
import com.example.quickidenti.dto.client.clientAuth
import com.example.quickidenti.dto.client.clientInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Client {
    @POST("/client/registration")
    suspend fun regClient(@Body regRequest: ClientReg): Boolean

    @POST("client/client_authentication/")
    suspend fun authClient(@Body authRequest: clientAuth): Boolean

    @GET("client/client_get/{client_email}")
    suspend fun infoClient(@Path("client_email") client_email: String): clientInfo

    @PUT("client/client_update/{client_email}")
    suspend fun changeClientData(@Path("client_email") client_email: String,
                                 @Body changeData: ClientChangeData): Boolean
}