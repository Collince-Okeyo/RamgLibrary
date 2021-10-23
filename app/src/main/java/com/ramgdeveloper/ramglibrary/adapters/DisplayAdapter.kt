package com.ramgdeveloper.ramglibrary.adapters

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.data.Books
import com.ramgdeveloper.ramglibrary.data.Category
import com.ramgdeveloper.ramglibrary.databinding.DisplayRowBinding
import com.ramgdeveloper.ramglibrary.databinding.HomeRowBinding
import com.ramgdeveloper.ramglibrary.fragments.home.HomeFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import android.app.DownloadManager
import android.content.Context
import android.net.Uri


private const val TAG = "DisplayAdapter"
class DisplayAdapter: ListAdapter<Books, DisplayAdapter.MyViewHolder>(MyDiffUtil){

    val pdfRef = Firebase.storage.reference
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


        val downloadPdf = holder.itemView.findViewById<ImageView>(R.id.dowloadImage)
        downloadPdf.setOnClickListener {
/*            CoroutineScope(Dispatchers.IO).launch {
                val maxDownloadSize = 5L * 1024 * 1024
                val bytes = pdfRef.child("books").getBytes(maxDownloadSize).await()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                Log.d(TAG, "onBindViewHolder: ${bitmap}")
            }
            */

        }

    }


    fun downloadFile(
        context: Context,
        fileName: String,
        fileExtension: String,
        destinationDirectory: String?,
        url: String?
    ): Long {
        val downloadmanager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(
            context,
            destinationDirectory,
            fileName + fileExtension
        )
        return downloadmanager.enqueue(request)
    }

}