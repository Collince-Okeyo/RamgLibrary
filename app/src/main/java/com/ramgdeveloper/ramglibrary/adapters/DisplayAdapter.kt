package com.ramgdeveloper.ramglibrary.adapters

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.data.Books
import com.ramgdeveloper.ramglibrary.databinding.DisplayRowBinding

private const val TAG = "DisplayAdapter"

class DisplayAdapter(val context: Context) :
    ListAdapter<Books, DisplayAdapter.MyViewHolder>(MyDiffUtil) {

    private lateinit var myPdf: String

    object MyDiffUtil : DiffUtil.ItemCallback<Books>() {
        override fun areItemsTheSame(oldItem: Books, newItem: Books): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Books, newItem: Books): Boolean {
            return oldItem.postId == newItem.postId
        }
    }

    inner class MyViewHolder(private val binding: DisplayRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(books: Books?) {
            binding.displayTitleTV.text = books?.title
            binding.displayDescriptionTV.text = books?.description
            myPdf = books?.bookUrl.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DisplayRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val books = getItem(position)
        holder.bind(books)

        // Downloading PDF
        val downloadPdf = holder.itemView.findViewById<ImageView>(R.id.dowloadImage)
        downloadPdf.setOnClickListener {
            Toast.makeText(context, "View mode...", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(myPdf), "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            val newIntent = Intent.createChooser(intent, "Open File")
            try {
                context.startActivity(newIntent)
            } catch (e: ActivityNotFoundException) {
                // Instruct the user to install a PDF reader here, or something
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}