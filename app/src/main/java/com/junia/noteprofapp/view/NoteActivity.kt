package com.junia.noteprofapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.junia.noteprofapp.R
import com.junia.noteprofapp.databinding.ActivityNoteBinding
import com.junia.noteprofapp.model.repository.NoteRepository
import com.junia.noteprofapp.viewmodel.NoteViewModel
import com.junia.noteprofapp.viewmodel.NoteViewModelFactory

class NoteActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteRepository: NoteRepository
    private lateinit var binding: ActivityNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_note)

        noteViewModel = ViewModelProvider(
            this,
            NoteViewModelFactory(noteRepository))[NoteViewModel::class.java]

        noteViewModel.notesViewModel.observe(this, Observer {
            binding.noteRecycler.adapter = NoteAdapter(it){}
        })
    }
}