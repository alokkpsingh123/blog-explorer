package com.example.blogexplorer.Room

import androidx.room.*
import com.example.blogexplorer.Models.Blog
import com.example.blogexplorer.Models.BlogList


@Dao
interface BlogDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlog(blog: Blog)

    @Query("SELECT * FROM Blog")
    suspend fun getAllBlogs() : List<Blog>

    @Update
    suspend fun updateBlog( blog: Blog)

    @Query("SELECT COUNT(*) FROM Blog WHERE id = :blogId")
    suspend fun doesBlogExist(blogId: String): Int




}