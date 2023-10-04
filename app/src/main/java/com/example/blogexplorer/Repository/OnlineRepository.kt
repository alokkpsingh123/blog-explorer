package com.example.blogexplorer.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.blogexplorer.Models.Blog
import com.example.blogexplorer.Models.BlogList
import com.example.blogexplorer.Retrofit.BlogAPI
import com.example.blogexplorer.Room.BlogDB
import com.example.blogexplorer.Utils.NetworkResult
import retrofit2.Response
import javax.inject.Inject

class OnlineRepository @Inject constructor(private val blogAPI: BlogAPI, private  val blogDB: BlogDB) {

    private val _blogLiveData = MutableLiveData<NetworkResult<BlogList>>()
    val blogLiveData : LiveData<NetworkResult<BlogList>>
    get() = _blogLiveData

//    private  var _blogListOffline : MutableList<Blog> = mutableListOf()
//    val blogListOffline : List<Blog>
//    get() = _blogListOffline

    private  var _blogListOffline = MutableLiveData<List<Blog>>()
    val blogListOffline : LiveData<List<Blog>>
        get() = _blogListOffline

    suspend fun getBlogs(){
        _blogLiveData.postValue(NetworkResult.Loading())
        val response = blogAPI.getBlogs()
        handleResponse(response)
    }

    suspend fun getBlogsFromOffline() {
        val blogList: List<Blog> = blogDB.getBlogDAO().getAllBlogs()
//        Log.d("alok", blogList.toString())
//        blogList.forEach {
//            _blogListOffline.add(it)
//        }
//        Log.d("alok", blogListOffline.toString())
        _blogListOffline.postValue(blogList)
    }

    private suspend fun insertBlog( response: Response<BlogList>){
        val blogList = response.body()!!
        blogList.blogs.forEach{
            val existingCount = blogDB.getBlogDAO().doesBlogExist(it.id)
            if (existingCount == 0) {
                blogDB.getBlogDAO().insertBlog(it)
            } else {
                blogDB.getBlogDAO().updateBlog(it)
            }
        }
    }

    suspend fun updateBlog(response: Response<BlogList>){
        val blogList = response.body()!!
        blogList.blogs.forEach{
            blogDB.getBlogDAO().updateBlog(it)
        }
    }

    private suspend fun handleResponse(response: Response<BlogList>){
        if (response.isSuccessful && response.body() != null) {
             insertBlog(response)
            Log.d("alok", response.body().toString())
            _blogLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            _blogLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        } else {
            _blogLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }


}