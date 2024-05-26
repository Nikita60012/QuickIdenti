package com.example.quickidenti.api

import com.example.quickidenti.dto.client.response.GetIdentification
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface Identification {
    @Multipart
    @POST("/identification_people/identification/{client_token}")
    suspend fun identification(@Path("client_token") clientToken: String,
                               @Part file: MultipartBody.Part?): Boolean

    @GET("/identification_people/identification_get/{client_token}/{identification_id}")
    suspend fun getIdentification(@Path("client_token") clientToken: String,
                                  @Path("identification_id") identificationId: Int): GetIdentification

    @GET("/identification_people/identification_get_known_person_photo/{client_token}/{identification_id}")
    suspend fun getIdentificationKnownPhoto(@Path("client_token") clientToken: String,
                                            @Path("identification_id") identificationId: Int): Boolean

    @GET("/identification_people/identification_get_unknown_person_photo/{client_token}/{identification_id}")
    suspend fun getIdentificationUnknownPhoto(@Path("client_token") clientToken: String,
                                              @Path("identification_id") identificationId: Int): Boolean
}