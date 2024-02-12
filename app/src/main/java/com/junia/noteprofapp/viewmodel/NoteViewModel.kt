package com.junia.noteprofapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junia.noteprofapp.model.entity.Note
import com.junia.noteprofapp.model.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    val notesViewModel : LiveData<List<Note>> = noteRepository.notes

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.insertNote(note)
    }

    fun deleteNote(id : Int) = viewModelScope.launch(Dispatchers.IO){
        noteRepository.deleteNote(id)
    }

    fun deleteAllNote() = viewModelScope.launch(Dispatchers.IO){
        noteRepository.deleteAllNote()
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.updateNote(note)
    }

    fun searchNote(searchQuery: String?) : LiveData<List<Note>>{
        return noteRepository.searchNote(searchQuery)
    }

}