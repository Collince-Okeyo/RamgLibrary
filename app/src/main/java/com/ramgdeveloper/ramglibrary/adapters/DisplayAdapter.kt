package com.ramgdeveloper.ramglibrary.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ramgdeveloper.ramglibrary.data.Books
import com.ramgdeveloper.ramglibrary.data.Category
import com.ramgdeveloper.ramglibrary.databinding.DisplayRowBinding
import com.ramgdeveloper.ramglibrary.databinding.HomeRowBinding
import com.ramgdeveloper.ramglibrary.fragments.home.HomeFragmentDirections

class DisplayAdapter: ListAdapter<Books, DisplayAdapter.MyViewHolder>(MyDiffUtil){
    object MyDiffUtil: DiffUtil.ItemCallback<Books>() {
        override fun areItemsTheSame(oldItem: Books, newItem: Books): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Books, newItem: Books): Boolean {
            return oldItem.postId == newItem.postId
        }
    }
    inner class MyViewHolder(private val binding: DisplayRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(books: Books?) {
            binding.displayTitleTV.text = books?.title
            binding.displayDescriptionTV.text = books?.description
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(DisplayRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val books = getItem(position)
        holder.bind(books)

    }


}