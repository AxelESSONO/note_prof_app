package com.junia.noteprofapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.junia.noteprofapp.NoteApplication
import com.junia.noteprofapp.R
import com.junia.noteprofapp.common.Action
import com.junia.noteprofapp.common.Constants
import com.junia.noteprofapp.databinding.DeleteAlertBinding
import com.junia.noteprofapp.databinding.NoteManagementBinding
import com.junia.noteprofapp.model.entity.Note
import com.junia.noteprofapp.viewmodel.NoteViewModel
import com.junia.noteprofapp.viewmodel.NoteViewModelFactory

class NoteAdapter(
    private val noteViewModel: NoteViewModel
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private lateinit var notes : MutableList<Note>
    init {
        notes = mutableListOf()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val noteView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(noteView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindNote(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNote(notes: List<Note>?) {
        this.notes = notes as MutableList<Note>
        notifyDataSetChanged()
    }


    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

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

        fun bindNote(note: Note){
            noteText.text = note.text
            noteTitle.text = note.title
            editNoteImage.setOnClickListener {
                //showUpdateNoteAlert(itemView.context, Action.UPDATE, note)
            }
            deleteNoteImage.setOnClickListener {
                //deleteNote(itemView.context, note)

                val alertBuilder : AlertDialog.Builder = AlertDialog.Builder(itemView.context)
                val deleteAllNote : View = LayoutInflater.from(itemView.context).inflate(
                    R.layout.delete_alert,
                    null,
                    false
                )

                alertBuilder.setView(deleteAllNote)

                // create and show the alert dialog
                val dialog = alertBuilder.create()
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()

                val confirmButton : MaterialButton = deleteAllNote.findViewById(R.id.confirmButton)
                val cancelButton : MaterialButton = deleteAllNote.findViewById(R.id.cancelButton)

                cancelButton.setOnClickListener { dialog.dismiss()  }
                confirmButton.setOnClickListener {
                    noteViewModel.deleteNote(note.id)
                    Toast.makeText(
                        itemView.context,
                        "La note a été supprimée!",
                        Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
        }

        /*private fun showUpdateNoteAlert(context: Context, action: Action, note: Note) {

            if (action == Action.UPDATE){
                val builder = AlertDialog.Builder(context)
                val noteManagementBinding : NoteManagementBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(context),
                    R.layout.note_management,
                    null,
                    false
                )
                val customLayout = LayoutInflater.from(context).inflate(R.layout.note_management, null)
                builder.setView(noteManagementBinding.root)

                // create and show the alert dialog
                val dialog = builder.create()
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()

                noteManagementBinding.apply {
                    titleEdit.setText(note.title)
                    textEdit.setText(note.text)
                    confirmButton.text = Constants.UPDATE_THIS_NOTE
                }


                noteManagementBinding.cancelButton.setOnClickListener {
                    dialog.dismiss()
                }

                noteManagementBinding.confirmButton.setOnClickListener {
                    if (noteManagementBinding.titleEdit.text.isEmpty() || noteManagementBinding.textEdit.text.isEmpty()) {
                        Toast.makeText(context, "All fields are mandatory", Toast.LENGTH_SHORT).show()
                    } else {
                        val newNote = Note(
                            0,
                            noteManagementBinding.titleEdit.text.toString(),
                            noteManagementBinding.textEdit.text.toString()
                        )
                        noteViewModel.updateNote(newNote)
                        Toast.makeText(context, "Note updated successfully", Toast.LENGTH_SHORT).show()
                        Log.d("NOTE", newNote.title)
                        Log.d("NOTE", newNote.text)
                        dialog.dismiss()
                    }
                }
            }
            if (action == Action.DELETE){
                //deleteNote(context, note)
            }
        }*/


      /*  private fun deleteNote(context: Context, note: Note) {
            val builder = AlertDialog.Builder(context)
            val deleteAlertBinding : DeleteAlertBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.delete_alert,
                null,
                false
            )
            builder.setView(deleteAlertBinding.root)
            val dialog = builder.create()

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            deleteAlertBinding.textAlert.text = Constants.UPDATE_THIS_NOTE
            deleteAlertBinding.cancelButton.setOnClickListener { dialog.dismiss() }
            deleteAlertBinding.confirmButton.setOnClickListener {
                noteViewModel.deleteNote(note.id)
                dialog.dismiss()
            }
        }*/
    }
}















































