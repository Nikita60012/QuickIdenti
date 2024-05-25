package com.example.quickidenti.api

import com.example.quickidenti.dto.client.request.ClientChangeData
import com.example.quickidenti.dto.client.request.ClientReg
import com.example.quickidenti.dto.client.request.clientAuth
import com.example.quickidenti.dto.client.request.clientInfo
import com.example.quickidenti.dto.client.response.GetToken
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Client {
    @POST("/client/registration")
    suspend fun regClient(@Body regRequest: ClientReg): GetToken

    @POST("client/client_authentication/")
    suspend fun authClient(@Body authRequest: clientAuth): GetToken

    @GET("client/client_get/{client_token}")
    suspend fun infoClient(@Path("client_token") clientToken: String): clientInfo

    @PUT("client/client_update/{client_token}")
    suspend fun changeClientData(@Path("client_token") clientToken: String,
                                 @Body changeData: ClientChangeData): Boolean

    @PUT("client/client_exit/{client_token}")
    suspend fun clientExit(@Path("client_token") clientToken: String)
}