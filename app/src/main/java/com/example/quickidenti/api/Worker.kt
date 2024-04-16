package com.example.quickidenti.api

import com.example.quickidenti.dto.worker.WorkerGet
import retrofit2.http.GET

interface  Worker {
    @GET("/edit_workers/get/1")
     suspend fun getWorker(): WorkerGet
}