package com.example.blogexplorer


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.blogexplorer.Models.Blog
import com.example.blogexplorer.databinding.ItemBlogBinding
import com.squareup.picasso.Picasso


class BlogListAdapter : ListAdapter<Blog,BlogListAdapter.BlogViewHolder>(DiffUtil()) {

    var onItemClick : ((Blog) -> Unit)? = null
    var onBookMarkClick : ((View) -> Unit)? = null

    inner class BlogViewHolder(private val binding: ItemBlogBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(blog: Blog){
            Picasso.get().load(blog.image_url).into(binding.blogImage)
            binding.blogText.text = blog.title
            binding.btnBookMark.setOnClickListener{
                onBookMarkClick?.invoke(binding.btnBookMark)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val binding = ItemBlogBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return BlogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blog = getItem(position)
        blog?.let {
            holder.bind(blog)
        }

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(blog)
        }

    }

   class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Blog>(){
       override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
           return oldItem.id == newItem.id
       }

       override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
           return  oldItem == newItem
       }

   }

}