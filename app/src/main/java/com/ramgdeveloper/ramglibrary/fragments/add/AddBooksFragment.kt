package com.ramgdeveloper.ramglibrary.fragments.add

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.data.Books
import com.ramgdeveloper.ramglibrary.databinding.FragmentAddBooksBinding
import com.ramgdeveloper.ramglibrary.others.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

private const val TAG = "AddBooksFragment"

class AddBooksFragment : Fragment() {
    private lateinit var binding: FragmentAddBooksBinding
    private val firebaseStorage = Firebase.storage
    private val auth = FirebaseAuth.getInstance()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var pdfUri: Uri
    private lateinit var myCategory: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBooksBinding.inflate(inflater, container, false)

        databaseReference = FirebaseDatabase.getInstance().getReference("books")

        // Upload button
        binding.buttonAdd.setOnClickListener {
            Utils.hideKeyboard(it)

            when {
                TextUtils.isEmpty(binding.titleEditText.text.toString()) -> {
                    binding.titleEditText.error = "Title can't be blank"
                }
                TextUtils.isEmpty(binding.addDescriptionTV.text.toString()) -> {
                    binding.addDescriptionTV.error = "Please add description"
                }
                binding.spinnerCategory.selectedItem.toString() == "Choose Category" -> {
                    Toast.makeText(requireContext(), "Select Category", Toast.LENGTH_SHORT).show()
                }
                binding.pickTV.text.equals("Pick Book/PDF") -> {
                    Toast.makeText(requireContext(), "Pick book or pdf", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    try {
                        addBooksToFirebase()
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Pick Book
        binding.pickTV.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "application/pdf"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, 1212)
        })

        //Navigating back to Home
        binding.addBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_addBooksFragment_to_homeFragment)
        }

        // Category Spinner
        val spinner = binding.spinnerCategory
        if (spinner != null) {
            val adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.categories, android.R.layout.simple_spinner_item
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    myCategory = p0?.getItemAtPosition(p2).toString()
                    // binding.titleEditText.text = item as Editable?
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Toast.makeText(requireContext(), "Select Category", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            1212 -> if (resultCode === RESULT_OK) {

                pdfUri = data?.data!!
                val uri: Uri = data?.data!!
                val uriString: String = uri.toString()
                var displayName: String? = null
                if (uriString.startsWith("content://")) {
                    var cursor: Cursor? = null
                    try {
                        cursor =
                            requireActivity()!!.contentResolver.query(uri, null, null, null, null)
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName =
                                cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            binding.pickTV.text = displayName
                        }
                    } finally {
                        cursor?.close()
                    }
                }
            }
        }
    }

    private fun addBooksToFirebase() {
        val pd = ProgressDialog(requireContext())
        pd.setTitle("Uploading Contents...")
        pd.setCancelable(false)
        pd.show()

        CoroutineScope(Dispatchers.IO).launch {
            val uid = auth.uid!!
            val postId = UUID.randomUUID().toString()
            val pdf = firebaseStorage.getReference(postId).putFile(pdfUri).await()
            val pdfUrl = pdf?.metadata?.reference?.downloadUrl?.await().toString()
            Log.d(TAG, "addBooksToFirebase: $pdfUrl")

            val book = Books(
                postId,
                binding.titleEditText.text.toString(),
                binding.addDescriptionTV.text.toString(),
                binding.spinnerCategory.selectedItem.toString(),
                pdfUrl
            )

            //Database
            databaseReference.child(binding.spinnerCategory.selectedItem.toString()).push()
                .setValue(book).await()
            // binding.spinnerCategory.selectedItem.toString() = "Choose Category"
            withContext(Dispatchers.Main) {
                binding.pickTV.text = "Pick Book/PDF"
                binding.titleEditText.setText("")
                binding.addDescriptionTV.setText("")
                pd.dismiss()
            }
        }

    }
}