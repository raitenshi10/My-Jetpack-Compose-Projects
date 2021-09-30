package com.example.mynotes.feature_note.domain.uses_case

import com.example.mynotes.feature_note.domain.model.Note
import com.example.mynotes.feature_note.domain.repository.NoteRepository

class GetNoteById(
    val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}