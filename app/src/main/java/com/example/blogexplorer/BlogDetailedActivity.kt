package com.example.blogexplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.blogexplorer.Models.Blog
import com.example.blogexplorer.Utils.Constants
import com.example.blogexplorer.databinding.ActivityBlodDetailedBinding
import com.squareup.picasso.Picasso

class BlogDetailedActivity : AppCompatActivity() {

    private var _binding : ActivityBlodDetailedBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBlodDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val blog = intent?.getParcelableExtra<Blog>(Constants.NEXT_SCREEN)
        if(blog != null){
            Picasso.get().load(blog.image_url).into(binding.imgBlog)
            binding.txtTitle.text = blog.title
        }
    }


}