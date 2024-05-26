package com.example.quickidenti.dto.client.request

data class clientInfo(
    val email: String,
    val phone: String,
    val slots: Int,
    val days: Int,
    val slots_occupied: Int
)
