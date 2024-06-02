package com.example.quickidenti.api

import com.example.quickidenti.dto.People.PeopleInfo
import com.example.quickidenti.dto.People.PeopleList
import com.example.quickidenti.dto.client.response.CheckSubscribe
import com.example.quickidenti.dto.human.request.HumanAdd
import com.example.quickidenti.dto.human.request.HumanUpdate
import com.example.quickidenti.dto.human.response.StatusGet
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface People {
    @GET("/edit_people/people_get/{client_token}")
    suspend fun getPeoples(@Path("client_token") clientToken: String): MutableList<PeopleList>

    @GET("/edit_people/human_get/{client_token}/{human_id}")
    suspend fun getHuman(
        @Path("client_token") clientToken: String,
        @Path("human_id") humanId: Int
    ): PeopleInfo

    @DELETE("/edit_people/human_del/{client_token}/{human_id}")
    suspend fun delHuman(
        @Path("client_token") clientToken: String,
        @Path("human_id") humanId: Int
    ): Boolean

    @POST("/edit_people/human_add/{client_token}")
    suspend fun addHuman(
        @Path("client_token") clientToken: String,
        @Body addHuman: HumanAdd
    ): StatusGet

    @PUT("/edit_people/human_update/{client_token}/{human_id}")
    suspend fun updateHuman(
        @Path("client_token") clientToken: String,
        @Path("human_id") humanId: Int,
        @Body updHuman: HumanUpdate
    ): StatusGet

    @GET("client/client_check_subscribe/{client_token}")
    suspend fun checkSubscribe(@Path("client_token") clientToken: String): CheckSubscribe
}