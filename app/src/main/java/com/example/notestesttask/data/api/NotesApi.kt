package com.example.notestesttask.data.api

import com.example.notestesttask.model.Note
import com.google.firebase.firestore.DocumentSnapshot

interface NotesApi {
    fun getNotesByEmail(
        email: String,
        onSuccessListener: (document: List<DocumentSnapshot>) -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    )

    fun deleteNote(
        email: String,
        note: Note,
        onSuccessListener: (status: Boolean) -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    )

    fun saveNote(
        email: String,
        note: Note,
        onSuccessListener: (status: Boolean) -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    )

    fun getImages(
        onSuccessListener: (images: List<DocumentSnapshot>) -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    )
}