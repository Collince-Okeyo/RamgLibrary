package com.ramgdeveloper.ramglibrary.fragments.display

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentDisplayBinding

class DisplayFragment : Fragment() {
    private lateinit var binding: FragmentDisplayBinding
    val args: DisplayFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisplayBinding.inflate(inflater, container, false)


        args.booksDisplay.categoryName

        return binding.root
    }
}