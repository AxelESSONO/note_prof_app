package com.junia.noteprofapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.junia.noteprofapp.R
import com.junia.noteprofapp.model.entity.Note

class NoteAdapter(
    private var notes : List<Note>,
    private val listener : (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val noteView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(noteView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindNote(notes[position], listener)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private var noteTitle : TextView
        private var noteText : TextView
        private var editNoteImage : ImageView
        private var deleteNoteImage : ImageView

        init {
            noteTitle = itemView.findViewById(R.id.noteTitle)
            noteText = itemView.findViewById(R.id.noteText)
            editNoteImage = itemView.findViewById(R.id.editNoteImage)
            deleteNoteImage = itemView.findViewById(R.id.deleteNoteImage)
        }

        fun bindNote(note: Note, listener: (Note) -> Unit){
            noteText.text = note.text
            noteTitle.text = note.title
            editNoteImage.setOnClickListener {
                listener(note)
            }
            deleteNoteImage.setOnClickListener {
                listener(note)
            }
        }
    }
}