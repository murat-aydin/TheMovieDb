package com.murataydin.themoviedb.app.di

import android.content.Context
import androidx.room.Room
import com.murataydin.themoviedb.BuildConfig
import com.murataydin.themoviedb.data.local.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, MovieDatabase::class.java, BuildConfig.APPLICATION_ID + ".db"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideMovieDBDao(db: MovieDatabase) = db.movieDBDao()

}