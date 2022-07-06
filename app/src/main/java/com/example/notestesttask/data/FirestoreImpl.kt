package com.example.notestesttask.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notestesttask.data.api.NotesApi
import com.example.notestesttask.model.Note
import com.example.notestesttask.model.NotesList
import com.example.notestesttask.util.Constants
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreImpl constructor(fireStore: FirebaseFirestore) : NotesApi {

    private var fireStoreCollection = fireStore.collection(Constants.COLLECTION_NAME)

    override fun getNotesByEmail(
        email: String,
        onSuccessListener: (document: List<DocumentSnapshot>) -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    ) {
        fireStoreCollection.document(email).collection(Constants.SUB_COLLECTION_NAME)
            .whereEqualTo(Constants.ACTIVE_FIELD, true)
            .get()
            .addOnSuccessListener {
                onSuccessListener(it.documents)
            }
            .addOnFailureListener {
                onFailureListener(it)
            }
    }

    override fun deleteNote(
        email: String,
        note: Note,
        onSuccessListener: (status: Boolean) -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    ) {
        fireStoreCollection.document(email).collection(Constants.SUB_COLLECTION_NAME)
            .document(note.id)
            .update(Constants.ACTIVE_FIELD, false)
            .addOnSuccessListener {
                onSuccessListener(true)
            }

           .addOnFailureListener {
                onFailureListener(it)
            }
    }
}