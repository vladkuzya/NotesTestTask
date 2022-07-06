package com.example.notestesttask.data

import com.example.notestesttask.data.api.NotesApi
import com.example.notestesttask.model.Note
import com.example.notestesttask.util.Constants
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirestoreImpl constructor(fireStore: FirebaseFirestore) : NotesApi {

    private var imagesCollection = fireStore.collection(Constants.IMAGES_COLLECTION_NAME)
    private var notesCollection = fireStore.collection(Constants.NOTES_COLLECTION_NAME)

    override fun getNotesByEmail(
        email: String,
        onSuccessListener: (document: List<DocumentSnapshot>) -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    ) {
        notesCollection.document(email).collection(Constants.SUB_COLLECTION_NAME)
            .orderBy("date", Query.Direction.DESCENDING)
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
        notesCollection.document(email).collection(Constants.SUB_COLLECTION_NAME)
            .document(note.id)
            .update(Constants.ACTIVE_FIELD, false)
            .addOnSuccessListener {
                onSuccessListener(true)
            }

            .addOnFailureListener {
                onFailureListener(it)
            }
    }

    override fun saveNote(
        email: String,
        note: Note,
        onSuccessListener: (status: Boolean) -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    ) {
        notesCollection.document(email).collection(Constants.SUB_COLLECTION_NAME)
            .add(note)
            .addOnSuccessListener {
                onSuccessListener(true)
            }

            .addOnFailureListener {
                onFailureListener(it)
            }
    }

    override fun getImages(
        onSuccessListener: (images: List<DocumentSnapshot>) -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    ) {
        imagesCollection
            .get()
            .addOnSuccessListener {
                onSuccessListener(it.documents)
            }

            .addOnFailureListener {
                onFailureListener(it)
            }
    }
}