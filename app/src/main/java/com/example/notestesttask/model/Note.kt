package com.example.notestesttask.model

import java.util.*

data class Note(
    var id: String = "",
    val text: String = "",
    val date: Date = Date(),
    val image: String = "",
    val active: Boolean = true
) {

}
