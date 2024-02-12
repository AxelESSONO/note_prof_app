package com.junia.noteprofapp.model.repository
import androidx.lifecycle.LiveData
import com.junia.noteprofapp.model.dao.NoteDao
import com.junia.noteprofapp.model.entity.Note

class NoteRepository(
    private val noteDao: NoteDao
) {

    val notes : LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insertNote(note: Note){
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(id : Int){
        noteDao.deleteNote(id)
    }

    suspend fun deleteAllNote(){
        noteDao.deleteAllNote()
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }
    fun searchNote(searchQuery: String?) : LiveData<List<Note>>{
        return noteDao.searchNote(searchQuery)
    }
}