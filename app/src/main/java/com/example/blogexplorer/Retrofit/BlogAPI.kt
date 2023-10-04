package com.example.blogexplorer.Retrofit

import com.example.blogexplorer.Models.BlogList
import retrofit2.Response
import retrofit2.http.GET

interface BlogAPI {

    @GET("/api/rest/blogs/")
    suspend fun getBlogs(): Response<BlogList>
}