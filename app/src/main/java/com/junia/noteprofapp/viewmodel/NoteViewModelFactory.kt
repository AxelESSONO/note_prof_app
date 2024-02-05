package com.junia.noteprofapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.junia.noteprofapp.model.repository.NoteRepository

@Suppress("UNCHECKED_CAST")
class NoteViewModelFactory(
    private val noteRepository: NoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
            return NoteViewModel(noteRepository) as T
        }

        return throw IllegalArgumentException("Invalid Argument")
    }

}