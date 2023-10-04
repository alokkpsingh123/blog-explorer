package com.example.blogexplorer.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.blogexplorer.Models.Blog
import com.example.blogexplorer.Models.BlogList

@Database( entities = [Blog::class], version = 1)
abstract class BlogDB : RoomDatabase(){

    abstract fun getBlogDAO(): BlogDAO
}