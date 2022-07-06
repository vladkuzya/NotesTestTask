package com.example.notestesttask.screens.notes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notestesttask.R
import com.example.notestesttask.base.BaseFragment
import com.example.notestesttask.base.NotesApp
import com.example.notestesttask.data.FirestoreImpl
import com.example.notestesttask.databinding.NotesFragmentBinding
import com.example.notestesttask.util.MyViewModelFactory
import com.example.notestesttask.util.SwipeToDeleteCallback
import com.google.firebase.firestore.FirebaseFirestore

class NotesFragment : BaseFragment() {
    private lateinit var userEmail: String
    private lateinit var binding: NotesFragmentBinding
    private lateinit var viewModel: NotesViewModel
    private var adapter: NotesAdapter? = null

    override fun getContentView(): Int = R.layout.notes_fragment

    override fun setupDataBinding(dataBinding: ViewDataBinding?) {
        binding = dataBinding as NotesFragmentBinding
        binding.fragment = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(FirestoreImpl(FirebaseFirestore.getInstance()))
        )[NotesViewModel::class.java]
        userEmail = NotesApp.sharedPreferencesManager.getUserEmail()
        adapter = NotesAdapter()
        initObservers()
    }

    private fun initObservers() {
        viewModel.getIsLoading().observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.getAllNotes(userEmail).observe(viewLifecycleOwner) { list ->
            if (list != null && list.size > 0) {
                hideNoNotesInfo()
                adapter!!.setList(list)
                binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
                binding.rvNotes.adapter = adapter

                val swipeHandler = object : SwipeToDeleteCallback(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_delete_forever
                    )
                ) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        viewModel.deleteNote(
                            userEmail,
                            adapter!!.removeAt(viewHolder.adapterPosition)
                        ).observe(viewLifecycleOwner) { result ->
                            if (result) {
                                if (adapter!!.itemCount == 0) {
                                    showNoNotesInfo()
                                }
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.note_deleted),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                }
                val itemTouchHelper = ItemTouchHelper(swipeHandler)
                itemTouchHelper.attachToRecyclerView(binding.rvNotes)
            } else {
                showNoNotesInfo()
            }
        }

        viewModel.getError().observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), getErrorMessage(error), Toast.LENGTH_LONG).show()
        }
    }

    private fun showNoNotesInfo() {
        binding.tvMessage.visibility = View.VISIBLE
        binding.tvMessage.text = getString(R.string.no_notes)
    }

    private fun hideNoNotesInfo() {
        binding.tvMessage.visibility = View.GONE
    }

    fun onAddNoteClick() {
        NavHostFragment
            .findNavController(this)
            .navigate(NotesFragmentDirections.actionNotesFragmentToAddNoteFragment())
    }
}