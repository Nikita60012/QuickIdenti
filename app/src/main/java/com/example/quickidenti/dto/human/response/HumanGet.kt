package com.example.quickidenti.dto.human.response

data class HumanGet(
    val id: Int,
    val fullname: String,
    val birthdate: String,
    val phone: String,
    val photo: String
) {

}