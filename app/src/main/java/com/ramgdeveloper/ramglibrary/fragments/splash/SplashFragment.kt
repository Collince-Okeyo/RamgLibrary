package com.ramgdeveloper.ramglibrary.fragments.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentSplash2Binding

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplash2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSplash2Binding.inflate(inflater, container, false)

        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_screenOneFragment)
        }

        return binding.root
    }
}