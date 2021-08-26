package com.ramgdeveloper.ramglibrary.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ramgdeveloper.ramglibrary.databinding.FragmentScreenTwoBinding

class ScreenTwoFragment : Fragment() {
    private lateinit var binding: FragmentScreenTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScreenTwoBinding.inflate(inflater, container, false)

        return binding.root
    }
}