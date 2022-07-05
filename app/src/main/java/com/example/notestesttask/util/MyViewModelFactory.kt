package com.example.notestesttask.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notestesttask.data.api.NotesApi
import com.example.notestesttask.screens.notes.NotesViewModel

class MyViewModelFactory constructor(private val notesApi: NotesApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NotesViewModel::class.java) -> {
                NotesViewModel(notesApi) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel Not Found")

            }
        }
    }
}