package com.ramgdeveloper.ramglibrary.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentHomeBinding
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ramgdeveloper.ramglibrary.adapters.HomeAdapter
import com.ramgdeveloper.ramglibrary.data.Category

private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var categoryList: ArrayList<Category>
    private val adapter by lazy{HomeAdapter()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        loadCategories()
       // storageReference = FirebaseStorage.getInstance().getReference("images")
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addBooksFragment)
        }

        binding.homeOptionsMenu.setOnClickListener {
            showOptionsMenu(it)
        }
        return binding.root
    }
    // Show OptionsMenu
    fun showOptionsMenu(v : View){
        val popup = PopupMenu(requireContext(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.options_menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.share-> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                    intent.putExtra(Intent.EXTRA_TEXT, "Download my RamgLibrary app https://drive.google.com/file/d/1KS-5NbeDhrN0WPUKr1XzlfOCX1SeIGGK/view?usp=sharing")
                    startActivity(Intent.createChooser(intent, "Share App Via..."))
                }
                R.id.about-> {
                    val url = "https://github.com/Collince-Okeyo"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }
                R.id.help-> {
                   /* val intent = Intent(Intent.ACTION_VIEW)
                        .setType("plain/text")
                        .setData(Uri.parse("collinceokeyo98@gmail.com"))
                        .setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail")
                        .putExtra(Intent.EXTRA_SUBJECT, "Help")
                        .putExtra(Intent.EXTRA_TEXT, "Hello RamG developer....Please help me on how to use the application.");
                         startActivity(intent)*/
                }
                R.id.logout-> {
                    FirebaseAuth.getInstance().signOut()
                    Toast.makeText(requireContext(), "Logout Successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_homeFragment_to_logInFragment)
                }
            }
            true
        }
        popup.show()
    }

    //loading Categories
    private fun loadCategories(){
        databaseReference = FirebaseDatabase.getInstance().getReference("categories")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                categoryList = ArrayList()
                if (snapshot.exists()){
                    for (i in snapshot.children){
                        val category = i.getValue(Category::class.java)
                        Log.d(TAG, "onDataChange: OnDataChanged: $snapshot")

                        categoryList.add(category!!)
                    }
                    adapter.submitList(categoryList)
                    binding.recyclerView.adapter = adapter
                }else{
                    Log.d(TAG, "onDataChange: Failed")
                    Toast.makeText(requireContext(),
                        "Failed loading data", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: OnCancelled: "+error.message)
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}