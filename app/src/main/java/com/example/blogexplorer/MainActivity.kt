package com.example.blogexplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blogexplorer.Utils.Constants.NEXT_SCREEN
import com.example.blogexplorer.Utils.NetworkManager
import com.example.blogexplorer.Utils.NetworkResult
import com.example.blogexplorer.ViewModel.BlogViewModel
import com.example.blogexplorer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var blogViewModel: BlogViewModel
    lateinit var blogListAdapter: BlogListAdapter

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        blogListAdapter = BlogListAdapter()
        blogViewModel = ViewModelProvider(this)[BlogViewModel::class.java]
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.setHasFixedSize(true)
        blogViewModel.getBlog()

        val networkManager = NetworkManager(this)
        networkManager.observe(this) {
            if (it) {
                Toast.makeText(this, "Online", Toast.LENGTH_SHORT).show()
                bindObservers()
            } else {
                Toast.makeText(this, "Offline", Toast.LENGTH_SHORT).show()
                bindOffline()
            }
        }

    }

    private fun bindOffline() {
//        blogViewModel.getBlogsFromOffline()
//        val blogList = blogViewModel.blogListOffline
//        blogListAdapter.submitList(blogList)
//        handleNavigaitonToDetailedActivity()
//        handleBookmarkBtn()
//        binding.recycleView.adapter = blogListAdapter

        blogViewModel.getBlogsFromOffline()
        blogViewModel.blogOfflineLiveData.observe(this, Observer {
            blogListAdapter.submitList(it)
            binding.recycleView.adapter = blogListAdapter
            handleNavigaitonToDetailedActivity()
            handleBookmarkBtn()
        })
    }

    private fun bindObservers() {
        blogViewModel.blogLiveData.observe(this, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    val blogList = it.data!!.blogs
                    blogListAdapter.submitList(blogList)
                    handleNavigaitonToDetailedActivity()
                    handleBookmarkBtn()
                    binding.recycleView.adapter = blogListAdapter
                }
                is NetworkResult.Error -> {
                    Log.d("alok", "Something went wrong in error");
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun handleBookmarkBtn() {

        blogListAdapter.onBookMarkClick = {
            val TAG = it.tag
            Log.d("alok", TAG as String)
            if (TAG == "true") {
                it.setBackgroundResource(R.drawable.ic_bookmark_yellow)
                it.tag = "false"
                Toast.makeText(this, "Book Mark Added", Toast.LENGTH_SHORT).show()
            } else {
                it.setBackgroundResource(R.drawable.ic_bookmark_white)
                it.tag = "true"
                Toast.makeText(this, "Book Mark Removed", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun handleNavigaitonToDetailedActivity() {
        blogListAdapter.onItemClick = {
            val intent = Intent(this, BlogDetailedActivity::class.java)
            intent.putExtra(NEXT_SCREEN, it)
            startActivity(intent)

        }
    }
}


