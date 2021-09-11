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
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        databaseReference = FirebaseDatabase.getInstance().getReference("Caregories")
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

}