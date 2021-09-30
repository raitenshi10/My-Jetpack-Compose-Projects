package com.example.mynotes.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mynotes.ui.theme.*
import java.lang.Exception

@Entity(tableName = "tb_note")
data class Note(
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColor =
            listOf(
                RedOrange,
                RedPink,
                BabyBlue,
                Violet,
                DarkGrey
            )
    }
}

class InvalidNoteException(message: String): Exception(message)
