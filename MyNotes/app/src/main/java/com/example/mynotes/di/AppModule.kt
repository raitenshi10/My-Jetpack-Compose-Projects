package com.example.mynotes.di

import android.app.Application
import androidx.room.Room
import com.example.mynotes.feature_note.data.data_source.NoteDatabase
import com.example.mynotes.feature_note.data.repository.NoteRepositoryImpl
import com.example.mynotes.feature_note.domain.repository.NoteRepository
import com.example.mynotes.feature_note.domain.uses_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.ROOM_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repository: NoteRepository): NoteUseCase {
        return NoteUseCase(
            getNote = GetNote(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNoteId = GetNoteById(repository)
        )
    }
}