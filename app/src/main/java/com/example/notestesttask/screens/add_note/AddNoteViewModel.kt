package com.example.notestesttask.screens.add_note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notestesttask.data.api.NotesApi
import com.example.notestesttask.model.Image
import com.example.notestesttask.model.Note

class AddNoteViewModel(private val notesApi: NotesApi) : ViewModel() {
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val saveNoteLiveData = MutableLiveData<Boolean>()
    private val getImagesLiveData = MutableLiveData<MutableList<Image>>()
    private val errorLiveData = MutableLiveData<String>()

    fun saveNote(email: String, note: Note) {
        isLoadingLiveData.value = true
        notesApi.saveNote(
            email, note, {
                isLoadingLiveData.value = false
                saveNoteLiveData.value = it
            }, {
                isLoadingLiveData.value = false
                errorLiveData.value = it.localizedMessage
            }
        )
    }

    fun getImages() {
        notesApi.getImages( {
            val images = mutableListOf<Image>()
            for (documentSnapshot in it) {
                val image = documentSnapshot.toObject(Image::class.java)
                images.add(image!!)
            }
            getImagesLiveData.value = images
        }, {

        })
    }

    fun getIsLoading(): LiveData<Boolean> = isLoadingLiveData
    fun getSaveNoteLiveData(): LiveData<Boolean> = saveNoteLiveData
    fun getImagesLiveData(): LiveData<MutableList<Image>> = getImagesLiveData
    fun getErrorLiveData(): LiveData<String> = errorLiveData
}