package com.example.blogexplorer.Di

import android.content.Context
import androidx.room.Room
import com.example.blogexplorer.Room.BlogDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesBlogDB( @ApplicationContext context: Context) : BlogDB{
        return Room.databaseBuilder( context, BlogDB::class.java, "BlogDB").build()
    }
}