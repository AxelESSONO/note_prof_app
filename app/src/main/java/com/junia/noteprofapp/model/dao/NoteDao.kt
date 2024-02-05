package com.junia.noteprofapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.junia.noteprofapp.model.entity.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM note")
    fun getAllNotes() : LiveData<List<Note>>

    @Delete
    suspend fun deleteNote(id : Int)

    @Query("DELETE FROM note")
    suspend fun deleteAllNote()

    @Update
    suspend fun updateNote(note: Note)
}