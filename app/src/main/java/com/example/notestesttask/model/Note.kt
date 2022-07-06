package com.example.notestesttask.model

data class Note(
    var id: String = "",
    val text: String = "",
    val date: Long = 0,
    val image: String = "",
    val active: Boolean = true
)
