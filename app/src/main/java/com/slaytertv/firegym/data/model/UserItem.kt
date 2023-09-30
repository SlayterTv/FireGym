package com.slaytertv.firegym.data.model

data class UserItem(
    var id: String = "",
    val first_name: String = "",
    val last_name: String = "",
    val location: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = ""
)