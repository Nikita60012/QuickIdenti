package com.example.quickidenti.dao

import java.sql.Date

data class Worker(
    val id: Int,
    val fullname: String,
    val birthdate: Date,
    val phone: String,
    val photo: Array<Byte>,
    val descriptor: Array<Float>
)
