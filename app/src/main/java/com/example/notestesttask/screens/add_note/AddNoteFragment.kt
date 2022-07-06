package com.example.notestesttask.screens.add_note

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.notestesttask.R
import com.example.notestesttask.base.BaseFragment
import com.example.notestesttask.base.NotesApp
import com.example.notestesttask.data.FirestoreImpl
import com.example.notestesttask.databinding.AddNoteFragmentBinding
import com.example.notestesttask.model.Image
import com.example.notestesttask.model.Note
import com.example.notestesttask.screens.dialogs.ChooseImageDialog
import com.example.notestesttask.util.KeyboardUtil
import com.example.notestesttask.util.MyViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class AddNoteFragment : BaseFragment() {
    private lateinit var binding: AddNoteFragmentBinding
    private lateinit var viewModel: AddNoteViewModel
    private lateinit var dialog: ChooseImageDialog
    private lateinit var imagesList: MutableList<Image>
    private var imageUrl: String = ""

    override fun getContentView(): Int = R.layout.add_note_fragment

    override fun setupDataBinding(dataBinding: ViewDataBinding?) {
        binding = dataBinding as AddNoteFragmentBinding
        binding.fragment = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this, MyViewModelFactory(FirestoreImpl(FirebaseFirestore.getInstance()))
        )[AddNoteViewModel::class.java]
        initObservers()
        viewModel.getImages()
    }

    private fun initObservers() {
        viewModel.getIsLoading().observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.getSaveNoteLiveData().observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), getString(R.string.note_added), Toast.LENGTH_SHORT)
                    .show()
                onBackClick()
            }
        }

        viewModel.getImagesLiveData().observe(viewLifecycleOwner) {
            imagesList = it
        }

        viewModel.getErrorLiveData().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getErrorMessage(it), Toast.LENGTH_LONG).show()
        }
    }

    fun onSaveClick() {
        viewModel.saveNote(
            email = NotesApp.sharedPreferencesManager.getUserEmail(),
            note = Note(
                text = binding.etText.text.toString(),
                date = System.currentTimeMillis(),
                image = imageUrl
            )
        )
    }

    fun onBackClick() {
        KeyboardUtil.hideKeyboard(requireActivity())
        NavHostFragment.findNavController(this).navigateUp()
    }

    fun onAttachImageClick() {
        dialog = ChooseImageDialog(
            imagesList
        ) {
            imageUrl = it
            dialog.dismiss()
            Glide.with(binding.root.context)
                .load(it)
                .apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.btnAttachImage)
        }
        dialog.show(
            childFragmentManager,
            tag
        )
    }

}