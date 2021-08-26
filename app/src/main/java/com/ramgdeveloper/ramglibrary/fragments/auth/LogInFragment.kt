package com.ramgdeveloper.ramglibrary.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramgdeveloper.ramglibrary.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {
    private lateinit var binding: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)

        return binding.root
    }
}