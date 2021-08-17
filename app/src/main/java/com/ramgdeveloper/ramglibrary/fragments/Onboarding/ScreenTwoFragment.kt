package com.ramgdeveloper.ramglibrary.fragments.Onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentScreenTwoBinding

class ScreenTwoFragment : Fragment() {
    private lateinit var binding: FragmentScreenTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScreenTwoBinding.inflate(inflater, container, false)
        binding.buttonNext2.setOnClickListener {
            findNavController().navigate(R.id.action_screenTwoFragment_to_screenThreeFragment)
        }
        return binding.root
    }
}