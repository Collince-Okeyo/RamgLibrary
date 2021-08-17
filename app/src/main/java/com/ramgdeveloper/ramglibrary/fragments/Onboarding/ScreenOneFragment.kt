package com.ramgdeveloper.ramglibrary.fragments.Onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentScreenOneBinding

class ScreenOneFragment : Fragment() {
    private lateinit var binding: FragmentScreenOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScreenOneBinding.inflate(inflater, container, false)

        binding.buttonNext2.setOnClickListener {
            findNavController().navigate(R.id.action_screenOneFragment_to_screenTwoFragment)
        }

        return binding.root
    }
}