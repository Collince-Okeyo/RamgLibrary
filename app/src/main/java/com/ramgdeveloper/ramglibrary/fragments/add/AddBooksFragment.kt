package com.ramgdeveloper.ramglibrary.fragments.add

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

class AddBooksFragment : Fragment() {
    private lateinit var binding: FragmentAddBooksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBooksBinding.inflate(inflater, container, false)

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
                    TODO("Not yet implemented")
                }
            }
        }

        return  binding.root
    }
}