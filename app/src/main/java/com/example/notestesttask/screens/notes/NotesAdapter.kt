package com.example.notestesttask.screens.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.notestesttask.R
import com.example.notestesttask.databinding.NoteItemBinding
import com.example.notestesttask.model.Note
import com.example.notestesttask.util.Constants
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter(private var notesList: MutableList<Note>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = DataBindingUtil.inflate<NoteItemBinding>(
            LayoutInflater.from(parent.context), R.layout.note_item, parent, false
        )
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notesList[position])
    }

    override fun getItemCount(): Int = notesList.size

    fun removeAt(position: Int): Note {
        val note = notesList[position]
        notesList.remove(note)
        notifyItemRemoved(position)
        return note
    }

    class NotesViewHolder constructor(
        private var binding: NoteItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.tvText.text = note.text
            binding.tvDate.text =
                SimpleDateFormat(
                    Constants.DATE_FORMAT,
                    if (binding.root.context.resources.configuration.locale.country == "UA")
                        Locale("uk", "UA") else Locale.getDefault()
                )
                    .format(note.date)
            Glide.with(binding.root.context)
                .load(note.image)
                .apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.ivImage)
        }
    }
}