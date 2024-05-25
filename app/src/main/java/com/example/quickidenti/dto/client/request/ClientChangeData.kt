package com.example.quickidenti.dto.client.request

data class ClientChangeData(
    var email: String,
    var new_password: String,
    var old_password: String,
    var phone: String,
)
