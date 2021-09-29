package com.ramgdeveloper.ramglibrary.fragments.add

import android.R.attr
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentAddBooksBinding
import android.content.Intent
import android.widget.Toast

import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.navigation.fragment.findNavController
import android.provider.OpenableColumns

import android.R.attr.data
import android.app.Activity.RESULT_OK
import android.database.Cursor
import android.net.Uri
import java.io.File
import android.app.ProgressDialog
import android.text.TextUtils
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ramgdeveloper.ramglibrary.others.Utils


class AddBooksFragment : Fragment() {
    private lateinit var binding: FragmentAddBooksBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBooksBinding.inflate(inflater, container, false)

        // Upload button
        binding.buttonAdd.setOnClickListener {
            Utils.hideKeyboard(it)

            if (TextUtils.isEmpty(binding.titleEditText.text.toString())){
                binding.titleEditText.error = "Title can't be blank"
            }else if (TextUtils.isEmpty(binding.addDescriptionTV.text.toString())){
                binding.addDescriptionTV.error = "Please add description"
            }else if (binding.spinnerCategory.selectedItem.toString() == "Choose Category"){
                Toast.makeText(requireContext(), "Select Category", Toast.LENGTH_SHORT).show()
            }else{
                try {
                    addBooksToFirebase()
                } catch (e: Exception){
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
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

        binding.addBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_addBooksFragment_to_homeFragment)
        }

        // Category Spinner
        val spinner = binding.spinnerCategory
        if (spinner != null) {
            val adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.categories, android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val item = p0?.getItemAtPosition(p2)
                   // binding.titleEditText.text = item as Editable?
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Toast.makeText(requireContext(), "Select Category", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return  binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            1212 -> if (resultCode === RESULT_OK) {
                // Get the Uri of the selected file
                val uri: Uri = data?.data!!
                val uriString: String = uri.toString()
                val myFile: File = File(uriString)
                val path: String = myFile.name
                var displayName: String? = null
                if (uriString.startsWith("content://")) {
                    var cursor: Cursor? = null
                    try {
                        cursor = requireActivity()!!.contentResolver.query(uri, null, null, null, null)
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            binding.pickTV.text = displayName
                        }
                    } finally {
                        cursor?.close()
                    }
                } else if (uriString.endsWith("file://")) {
                    displayName = myFile.name
                    //binding.pickTV.text = displayName
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun addBooksToFirebase(){
        val pd = ProgressDialog(requireContext())
        pd.setTitle("Uploading Contents...")
        pd.setCancelable(false)
        pd.show()



    }
}