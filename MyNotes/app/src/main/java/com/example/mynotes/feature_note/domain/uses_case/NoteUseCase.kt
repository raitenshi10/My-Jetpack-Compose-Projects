package com.example.mynotes.feature_note.domain.uses_case

data class NoteUseCase(
    val getNote: GetNote,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNoteId: GetNoteById
)