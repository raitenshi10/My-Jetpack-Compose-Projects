package com.example.mynotes.feature_note.data.data_source

import androidx.room.*
import com.example.mynotes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM tb_note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM tb_note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}