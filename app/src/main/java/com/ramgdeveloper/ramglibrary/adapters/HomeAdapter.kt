package com.ramgdeveloper.ramglibrary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramgdeveloper.ramglibrary.data.Category
import com.ramgdeveloper.ramglibrary.databinding.HomeRowBinding

class HomeAdapter : ListAdapter<Category, HomeAdapter.MyViewHolder>(MyDiffUtil){
    object MyDiffUtil: DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.categoryName == newItem.categoryName
        }
    }
    inner class MyViewHolder(private val binding: HomeRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(categories: Category?) {
            binding.categoryNameTv.text = categories?.categoryName
            Glide.with(binding.categoryImageView)
                .load(categories?.categoryImage)
                .centerCrop()
                .into(binding.categoryImageView)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(HomeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val categories = getItem(position)
        holder.bind(categories)
    }
}