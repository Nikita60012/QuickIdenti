package com.example.quickidenti.api

import com.example.quickidenti.dto.People.PeopleInfo
import com.example.quickidenti.dto.People.PeopleList
import retrofit2.http.GET
import retrofit2.http.Path

interface People {
    @GET("/edit_workers/get_all/{user_email}")
    suspend fun getPeoples(@Path("user_email") user_email: String): MutableList<PeopleList>

    @GET("/edit_workers/get/{user_email}/{worker_id}")
    suspend fun getPeople(@Path("user_email") user_email: String, @Path("worker_id") worker_id: Int): PeopleInfo
}