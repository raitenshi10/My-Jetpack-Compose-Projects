package com.example.mynotes.feature_note.presentation.notes

import com.example.mynotes.feature_note.domain.model.Note
import com.example.mynotes.feature_note.domain.util.NoteOrder

sealed class NoteEvent {
    data class Order(val noteOrder: NoteOrder): NoteEvent()
    data class DeleteNote(val note: Note): NoteEvent()
    object RestoredNote: NoteEvent()
    object ToggleOrderedSection: NoteEvent()
}
