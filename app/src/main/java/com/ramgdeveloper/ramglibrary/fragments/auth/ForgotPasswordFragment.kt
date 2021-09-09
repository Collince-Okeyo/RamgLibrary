package com.ramgdeveloper.ramglibrary.fragments.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentForgotPasswordBinding
import com.ramgdeveloper.ramglibrary.others.Utils

class ForgotPasswordFragment : DialogFragment() {
    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        binding.textViewConfirm.setOnClickListener {
            Utils.hideKeyboard(it)

            if (binding.editTextTextEmailAddress.text.toString().isEmpty()){
                binding.editTextTextEmailAddress.error = "Enter email address"
            }
            FirebaseAuth.getInstance().sendPasswordResetEmail(binding.editTextTextEmailAddress.text.toString()).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(requireContext(), "Please check your email", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
                dismiss()
            }

        }

        binding.textViewCancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
/*
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }*/
}