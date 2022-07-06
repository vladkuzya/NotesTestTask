package com.example.notestesttask.screens.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notestesttask.data.api.NotesApi
import com.example.notestesttask.model.Note

class NotesViewModel(private val notesApi: NotesApi) : ViewModel() {

    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val notesLiveData = MutableLiveData<MutableList<Note>>()
    private val deleteNoteLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<String>()

    fun getAllNotes(email: String): LiveData<MutableList<Note>> {
        isLoadingLiveData.value = true
        notesApi.getNotesByEmail(email, { documents ->
            isLoadingLiveData.value = false
            val notesList = mutableListOf<Note>()
            for (document in documents) {
                val note = document.toObject(Note::class.java)
                note!!.id = document.id
                notesList.add(note)
            }
            notesLiveData.value = notesList
        }, { exception ->
            isLoadingLiveData.value = false
            errorLiveData.value = exception.localizedMessage
        })
        return notesLiveData
    }

    fun deleteNote(email: String, note: Note): LiveData<Boolean> {
        notesApi.deleteNote(email, note, { status ->
            deleteNoteLiveData.value = status
        }, { exception ->
            errorLiveData.value = exception.localizedMessage
        })
        return deleteNoteLiveData
    }

    fun getIsLoading(): LiveData<Boolean> = isLoadingLiveData
    fun getError(): LiveData<String> = errorLiveData
}