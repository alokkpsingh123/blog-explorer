package com.example.blogexplorer.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogexplorer.Models.Blog
import com.example.blogexplorer.Models.BlogList
import com.example.blogexplorer.Repository.OnlineRepository
import com.example.blogexplorer.Utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(val blogRepository: OnlineRepository):ViewModel() {

    val blogLiveData : LiveData<NetworkResult<BlogList>>
    get() = blogRepository.blogLiveData

//    val blogListOffline : List<Blog>
//    get() =  blogRepository.blogListOffline

    val blogOfflineLiveData : LiveData<List<Blog>>
        get() = blogRepository.blogListOffline

    fun getBlog(){
        viewModelScope.launch {
            blogRepository.getBlogs()
        }
    }

    fun getBlogsFromOffline(){
        viewModelScope.launch {
            blogRepository.getBlogsFromOffline()
        }
    }


}