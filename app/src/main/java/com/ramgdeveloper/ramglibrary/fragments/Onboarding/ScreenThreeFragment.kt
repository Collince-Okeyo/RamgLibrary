package com.ramgdeveloper.ramglibrary.fragments.Onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentScreenThreeBinding

class ScreenThreeFragment : Fragment() {
    private lateinit var binding: FragmentScreenThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentScreenThreeBinding.inflate(inflater, container, false)
        binding.buttonFinish.setOnClickListener {
            findNavController().navigate(R.id.action_screenThreeFragment_to_logInFragment)
        }
        return binding.root
    }
}