package com.example.quickidenti.api

import android.graphics.Bitmap
import com.example.quickidenti.dto.People.PeopleInfo
import com.example.quickidenti.dto.People.PeopleList
import com.example.quickidenti.dto.human.HumanAdd
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface People {
    @GET("/edit_workers/get_all/{user_email}")
    suspend fun getPeoples(@Path("user_email") user_email: String): MutableList<PeopleList>

    @GET("/edit_workers/get/{user_email}/{worker_id}")
    suspend fun getHuman(@Path("user_email") user_email: String, @Path("worker_id") worker_id: Int): PeopleInfo

    @DELETE("/edit_workers/del/{user_email}/{human_id}")
    suspend fun delHuman(@Path("user_email") user_email: String, @Path("human_id") human_id: Int): Boolean

    @POST("/edit_workers/add_photo/photo")
    suspend fun photoAdd(@Body photo: MultipartBody.Part): Boolean

    @Multipart
    @POST("/edit_workers/add/{user_email}")
    suspend fun addHuman(@Path("user_email") user_email: String,
                         @Part("addHuman") addHuman: HumanAdd,
                         @Part("photo") photo: Bitmap): Boolean
}