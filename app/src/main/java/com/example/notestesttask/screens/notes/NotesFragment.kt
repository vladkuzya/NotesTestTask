package com.example.notestesttask.screens.notes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notestesttask.R
import com.example.notestesttask.base.BaseFragment
import com.example.notestesttask.base.NotesApp
import com.example.notestesttask.data.FirestoreImpl
import com.example.notestesttask.databinding.NotesFragmentBinding
import com.example.notestesttask.model.Note
import com.example.notestesttask.util.Constants
import com.example.notestesttask.util.MyViewModelFactory
import com.example.notestesttask.util.SwipeToDeleteCallback
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Date
import java.sql.Timestamp

class NotesFragment : BaseFragment() {
    private lateinit var userEmail: String
    private lateinit var binding: NotesFragmentBinding
    private lateinit var viewModel: NotesViewModel
    private lateinit var fireStore: FirebaseFirestore
    private lateinit var notesCollection: CollectionReference

    override fun getContentView(): Int = R.layout.notes_fragment

    override fun setupDataBinding(dataBinding: ViewDataBinding?) {
        binding = dataBinding as NotesFragmentBinding
        binding.fragment = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fireStore = FirebaseFirestore.getInstance()
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(FirestoreImpl(fireStore))
        )[NotesViewModel::class.java]
        userEmail = NotesApp.sharedPreferencesManager.getUserEmail()
        initObservers()

//        initData()
    }

    private fun initObservers() {
        viewModel.getAllNotes(userEmail).observe(viewLifecycleOwner) { list ->
            if (list != null) {
                val adapter = NotesAdapter(list)
                binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
                binding.rvNotes.adapter = adapter

                val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        viewModel.deleteNote(
                            userEmail,
                            adapter.removeAt(viewHolder.adapterPosition)
                        ).observe(viewLifecycleOwner) { result ->
                            if (result) {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.note_deleted),
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                    }
                }
                val itemTouchHelper = ItemTouchHelper(swipeHandler)
                itemTouchHelper.attachToRecyclerView(binding.rvNotes)
            } else {
                binding.tvMessage.visibility = View.GONE
                binding.tvMessage.text = getString(R.string.no_notes)
            }
        }

        viewModel.getError().observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), getErrorMessage(error), Toast.LENGTH_LONG).show()
        }
    }

    fun onAddNoteClick() {

    }

    private fun initData() {
        notesCollection = fireStore.collection(Constants.COLLECTION_NAME)
        val stamp = Timestamp(System.currentTimeMillis())
        val date = Date(stamp.time)
        val userCollection = notesCollection.document(userEmail).collection("notes")
        userCollection.add(
            Note(
                text = "My text example1",
                date = date,
                image = Constants.TEST_IMAGE_URL
            )
        )
        userCollection.add(
            Note(
                text = "My text example2",
                date = date,
                image = Constants.TEST_IMAGE_URL
            )
        )

        userCollection.add(
            Note(
                text = "My text example3",
                date = date,
                image = Constants.TEST_IMAGE_URL
            )
        )

        userCollection.add(
            Note(
                text = "My text example4",
                date = date,
                image = Constants.TEST_IMAGE_URL,
                active = false
            )
        )
    }
}