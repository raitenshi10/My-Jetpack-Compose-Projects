package com.example.mynotes.feature_note.domain.uses_case

import com.example.mynotes.feature_note.domain.model.InvalidNoteException
import com.example.mynotes.feature_note.domain.model.Note
import com.example.mynotes.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        when {
            note.title.isBlank() -> throw InvalidNoteException("Title Can't Be Blank")
            note.content.isBlank() -> throw InvalidNoteException("Content Can't Be Blank")
            else -> repository.insertNote(note)
        }
    }
}