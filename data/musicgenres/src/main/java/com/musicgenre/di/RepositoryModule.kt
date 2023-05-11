package com.musicgenre.di

import com.musicgenre.repository.IMusicGenreRepository
import com.musicgenre.repository.MusicGenreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMusicGenreRepository(musicGenreRepositoryImpl: MusicGenreRepositoryImpl): IMusicGenreRepository
}