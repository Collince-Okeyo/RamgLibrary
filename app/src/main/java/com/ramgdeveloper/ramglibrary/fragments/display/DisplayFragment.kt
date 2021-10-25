package com.ramgdeveloper.ramglibrary.fragments.display

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.adapters.DisplayAdapter
import com.ramgdeveloper.ramglibrary.data.Books
import com.ramgdeveloper.ramglibrary.databinding.FragmentDisplayBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


private const val TAG = "DisplayFragment"
class DisplayFragment : Fragment() {
    private lateinit var binding: FragmentDisplayBinding
    private val adapter:DisplayAdapter by lazy { DisplayAdapter(requireContext()) }
    private lateinit var databaseReference: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisplayBinding.inflate(inflater, container, false)

        binding.displayBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_displayFragment_to_homeFragment)
        }

        val details = DisplayFragmentArgs.fromBundle(requireArguments()).categoryName

        databaseReference = FirebaseDatabase.getInstance().getReference("books")


        getBooks(details.categoryName!!)

        //args.booksDisplay.categoryName

        return binding.root
    }

    private fun getBooks(category: String) {
        binding.progressBar3.visibility = VISIBLE
        binding.loadingTV.visibility = VISIBLE
        val booksList = ArrayList<Books>()
        CoroutineScope(Dispatchers.Main).launch {
            val result = databaseReference.child(category).get().await()
            Log.d(TAG, "getBooks: $result")
            for (i in result.children){
                val book = i.getValue(Books::class.java)
                Log.d(TAG, "getBooks: ${book?.title}")
                booksList.add(book!!)
                Log.d(TAG, "getBooks: $booksList")
            }
            //
            adapter.submitList(booksList)
            binding.recyclerView2.adapter = adapter
            binding.progressBar3.visibility = INVISIBLE
            binding.loadingTV.visibility = INVISIBLE
        }
    }
}