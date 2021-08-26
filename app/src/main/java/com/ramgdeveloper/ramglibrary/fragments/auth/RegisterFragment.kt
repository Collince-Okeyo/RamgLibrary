package com.ramgdeveloper.ramglibrary.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentHomeBinding
import com.ramgdeveloper.ramglibrary.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }
}