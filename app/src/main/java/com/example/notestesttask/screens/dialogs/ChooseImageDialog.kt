package com.example.notestesttask.screens.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notestesttask.R
import com.example.notestesttask.databinding.DialogChooseMediaBinding
import com.example.notestesttask.model.Image


class ChooseImageDialog constructor(
    private var listImages: MutableList<Image>,
    private var onImageClick: (url: String) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogChooseMediaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_choose_media, container, false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImageRecyclerView()
    }

    private fun initImageRecyclerView() {
        binding.rvImages.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvImages.adapter = ImageAdapter(listImages, onImageClick)
    }
}