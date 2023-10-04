package com.example.blogexplorer.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

data class BlogList(
    val blogs: List<Blog>
)