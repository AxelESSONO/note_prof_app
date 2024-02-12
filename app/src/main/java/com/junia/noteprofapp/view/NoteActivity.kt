package com.junia.noteprofapp.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.button.MaterialButton
import com.junia.noteprofapp.NoteApplication
import com.junia.noteprofapp.R
import com.junia.noteprofapp.common.Action
import com.junia.noteprofapp.common.Constants
import com.junia.noteprofapp.databinding.ActivityNoteBinding
import com.junia.noteprofapp.databinding.DeleteAlertBinding
import com.junia.noteprofapp.databinding.NoteManagementBinding
import com.junia.noteprofapp.model.entity.Note
import com.junia.noteprofapp.model.repository.NoteRepository
import com.junia.noteprofapp.viewmodel.NoteViewModel
import com.junia.noteprofapp.viewmodel.NoteViewModelFactory

class NoteActivity : AppCompatActivity() {

   /* private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NoteApplication).noteRepository)
    }*/
    //private lateinit var binding: ActivityNoteBinding
    //private lateinit var noteAdapter: NoteAdapter

    private val noteViewModel : NoteViewModel by viewModels {
        NoteViewModelFactory((application as NoteApplication).noteRepository)
    }

    private lateinit var binding: ActivityNoteBinding
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_note)
        noteAdapter = NoteAdapter(noteViewModel)
        setSupportActionBar(binding.noteToolbar)

        setNoteRecycler()

        // Affichage des notes
        noteViewModel.notesViewModel.observe(this, Observer { notes ->
            if (notes.isNotEmpty()){
                binding.noDataFound.visibility = View.GONE
                binding.noteRecycler.visibility = View.VISIBLE
                noteAdapter.setNote(notes)
            }else{
                binding.noDataFound.visibility = View.VISIBLE
                binding.noteRecycler.visibility = View.GONE
            }
        })

        binding.addNewNote.setOnClickListener{
            val alertBuilder : AlertDialog.Builder = AlertDialog.Builder(this)
            val addNewNote : View = layoutInflater.inflate(
                R.layout.note_management,
                null,
                false
            )

            alertBuilder.setView(addNewNote)

            // create and show the alert dialog
            val dialog = alertBuilder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            // Initialiser les composants
            val titleEdit : EditText = addNewNote.findViewById(R.id.titleEdit)
            val textEdit : EditText = addNewNote.findViewById(R.id.textEdit)
            val confirmButton : MaterialButton = addNewNote.findViewById(R.id.confirmButton)
            val cancelButton : MaterialButton = addNewNote.findViewById(R.id.cancelButton)

            // Save Note
            cancelButton.setOnClickListener { dialog.dismiss() }
            confirmButton.setOnClickListener {
                if (
                    titleEdit.text.toString().isEmpty() ||
                    textEdit.text.toString().isEmpty()
                ){
                    Toast.makeText(
                        this,
                        "Tous les champs sont obligatoires",
                        Toast.LENGTH_SHORT).show()
                }else{
                    val note = Note(
                        0,
                        titleEdit.text.toString(),
                        textEdit.text.toString()
                    )
                    noteViewModel.insertNote(note)
                    Toast.makeText(
                        this,
                        "Note insérée avec succès",
                        Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
        }


      /*  binding = DataBindingUtil.setContentView(this, R.layout.activity_note)

        noteAdapter = NoteAdapter(noteViewModel)

        // enable action bar
        setSupportActionBar(binding.noteToolbar)

        setNoteRecycler()


        noteViewModel.notesViewModel.observe(this, Observer { notes ->
            if (notes.isNotEmpty()) {
                binding.apply {
                    noteRecycler.visibility = View.VISIBLE
                    noDataFound.visibility = View.GONE
                }
                binding.noteRecycler.apply {
                    noteAdapter.setNotes(notes)
                }
            } else {
                binding.apply {
                    noteRecycler.visibility = View.GONE
                    noDataFound.visibility = View.VISIBLE
                }
            }
        })

        binding.addNewNote.setOnClickListener(this)
        binding.searchNote.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchNote(newText)
                return true
            }
        })*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.changeColorItemMenu ->{

            }
            R.id.deleteAllItemMenu ->{
                val alertBuilder : AlertDialog.Builder = AlertDialog.Builder(this)
                val deleteAllNote : View = layoutInflater.inflate(
                    R.layout.delete_alert,
                    null,
                    false
                )

                alertBuilder.setView(deleteAllNote)

                // create and show the alert dialog
                val dialog = alertBuilder.create()
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()

                val textAlert : TextView = deleteAllNote.findViewById(R.id.textAlert)
                val confirmButton : MaterialButton = deleteAllNote.findViewById(R.id.confirmButton)
                val cancelButton : MaterialButton = deleteAllNote.findViewById(R.id.cancelButton)

                textAlert.text = "Do you want to delete all notes ?"

                cancelButton.setOnClickListener { dialog.dismiss()  }
                confirmButton.setOnClickListener {
                    noteViewModel.deleteAllNote()
                    Toast.makeText(
                        this,
                        "Toutes les notes ont été supprimées!",
                        Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setNoteRecycler() {
        binding.noteRecycler.apply {
            adapter = noteAdapter
            layoutManager = GridLayoutManager(applicationContext, 2)
            setHasFixedSize(true)
        }
    }

    /*   private fun setNoteRecycler() {
           binding.noteRecycler.apply {
               adapter = noteAdapter
               layoutManager = GridLayoutManager(applicationContext, 2)
               setHasFixedSize(true)
           }
       }

       private fun searchNote(newText: String?) {
           val searchQuery = "%$newText%"
           noteViewModel.searchNote(searchQuery).observe(this, Observer { notes ->
               noteAdapter.setNotes(notes)
           })
       }

       override fun onClick(view: View?) {
           when (view?.id) {
               binding.addNewNote.id -> {
                   showAddNewNoteAlert(Action.INSERT)
               }
           }
       }*/

   /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.changeColorItemMenu -> {
                true
            }

            R.id.deleteAllItemMenu -> {
                deleteAllNote(Constants.DELETE_ALL, Action.DELETE_ALL)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAddNewNoteAlert(action: Action) {
        if (action == Action.INSERT) {

            val builder = AlertDialog.Builder(this)
            val noteManagementBinding: NoteManagementBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.note_management, null, false)

            builder.setView(noteManagementBinding.root)

            // create and show the alert dialog
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            noteManagementBinding.cancelButton.setOnClickListener { dialog.dismiss() }

            noteManagementBinding.confirmButton.setOnClickListener {
                if (noteManagementBinding.titleEdit.text.isEmpty() || noteManagementBinding.textEdit.text.isEmpty()) {
                    Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show()
                } else {
                    val newNote = Note(
                        0,
                        noteManagementBinding.titleEdit.text.toString(),
                        noteManagementBinding.textEdit.text.toString()
                    )

                    noteViewModel.insertNote(newNote)
                    Toast.makeText(this, "Note inserted successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
        }

        if (action == Action.DELETE_ALL) {
            deleteAllNote(Constants.DELETE_THIS_NOTE, Action.DELETE)
        }
    }

    private fun deleteAllNote(text: String, action: Action) {

        val builder = AlertDialog.Builder(this)

        val deleteAlertBinding: DeleteAlertBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.delete_alert,
            null,
            false
        )
        builder.setView(deleteAlertBinding.root)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        deleteAlertBinding.textAlert.text = text

        deleteAlertBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        deleteAlertBinding.confirmButton.setOnClickListener {
            if (action == Action.DELETE_ALL) {
                noteViewModel.deleteAllNote()
            }
            dialog.dismiss()
        }
    }*/
}











































