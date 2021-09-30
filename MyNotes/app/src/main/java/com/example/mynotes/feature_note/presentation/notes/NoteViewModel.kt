package com.example.mynotes.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.feature_note.domain.model.Note
import com.example.mynotes.feature_note.domain.uses_case.NoteUseCase
import com.example.mynotes.feature_note.domain.util.NoteOrder
import com.example.mynotes.feature_note.domain.util.OrderType
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase
) : ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNoteJob: Job? = null

    init {
        getNote(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class
                    && state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNote(event.noteOrder)
            }
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCase.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NoteEvent.RestoredNote -> {
                viewModelScope.launch {
                    noteUseCase.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NoteEvent.ToggleOrderedSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNote(noteOrder: NoteOrder) {
        getNoteJob?.cancel()
        getNoteJob = noteUseCase.getNote(noteOrder)
            .onEach { note ->
                _state.value = state.value.copy(
                    note = note,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}