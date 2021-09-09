package com.ramgdeveloper.ramglibrary.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentHomeBinding
import com.ramgdeveloper.ramglibrary.databinding.FragmentRegisterBinding
import com.ramgdeveloper.ramglibrary.others.Utils

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.registerButton.setOnClickListener {
            Utils.hideKeyboard(it)

            when{
                binding.firstNameET.editText?.text.toString().isEmpty()-> {
                    binding.firstNameET.editText?.error = "Enter First Name"
                }
                binding.lastNameET.editText?.text.toString().isEmpty()-> {
                    binding.firstNameET.editText?.error = "Enter Last Name"
                }
                binding.emailSignUpET.editText?.text.toString().isEmpty()-> {
                    binding.emailSignUpET.editText?.error = "Enter Last Email"
                }
                binding.passwordSignUpET.editText?.text.toString().isEmpty()-> {
                    binding.passwordSignUpET.editText?.error = "Enter Last Password"
                }
                binding.phoneET.editText?.text.toString().isEmpty()-> {
                    binding.phoneET.editText?.error = "Enter Last Number"
                }
                binding.passwordSignUpET.editText?.text.toString().length < 8-> {
                    binding.firstNameET.editText?.error = "Short Password"
                }
                else-> {
                }
            }
        }



        return binding.root
    }
}