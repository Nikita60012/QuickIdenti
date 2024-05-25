package com.example.quickidenti.api

import android.graphics.Bitmap
import com.example.quickidenti.dto.People.PeopleInfo
import com.example.quickidenti.dto.People.PeopleList
import com.example.quickidenti.dto.human.HumanAdd
import okhttp3.MultipartBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface People {
    @GET("/edit_people/people_get/{client_token}")
    suspend fun getPeoples(@Path("client_token") clientToken: String): MutableList<PeopleList>

    @GET("/edit_people/human_get/{client_token}/{human_id}")
    suspend fun getHuman(@Path("client_token") clientToken: String, @Path("human_id") humanId: Int): PeopleInfo

    @DELETE("/edit_people/human_del/{client_token}/{human_id}")
    suspend fun delHuman(@Path("client_token") clientToken: String, @Path("human_id") humanId: Int): Boolean

    @Multipart
    @POST("/edit_people/add_photo/photo")
    suspend fun photoAdd(@Part file: MultipartBody.Part?): Boolean

    @Multipart
    @POST("/edit_people/human_add/{client_token}")
    suspend fun addHuman(@Path("client_token") clientToken: String,
                         @Part("addHuman") addHuman: HumanAdd,
                         @Part("photo") photo: Bitmap): Boolean
}