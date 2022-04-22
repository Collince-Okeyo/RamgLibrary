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
import com.ramgdeveloper.ramglibrary.data.Category
import com.ramgdeveloper.ramglibrary.databinding.HomeRowBinding
import com.ramgdeveloper.ramglibrary.fragments.home.HomeFragmentDirections

class HomeAdapter(private val onClickListener: OnClickListener) : ListAdapter<Category, HomeAdapter.MyViewHolder>(MyDiffUtil){
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
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        //binding.progressBar.visibility = INVISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        //binding.progressBar.visibility = INVISIBLE
                        return false
                    }
                })
                .fitCenter()
                .into(binding.categoryImageView)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(HomeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val categories = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(categories)
        }
        holder.bind(categories)
    }

    class OnClickListener(val clickListener: (category: Category)-> Unit){
        fun onClick(category: Category) = clickListener(category)
    }
}