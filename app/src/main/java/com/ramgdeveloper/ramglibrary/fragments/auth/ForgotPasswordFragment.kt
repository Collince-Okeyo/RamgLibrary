package com.ramgdeveloper.ramglibrary.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.ramgdeveloper.ramglibrary.databinding.FragmentForgotPasswordBinding
import com.ramgdeveloper.ramglibrary.others.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordFragment : DialogFragment() {
    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        binding.textViewConfirm.setOnClickListener {
            Utils.hideKeyboard(it)

            if (binding.editTextTextEmailAddress.text.toString().isEmpty()) {
                binding.editTextTextEmailAddress.error = "Enter email address"
            } else {
            }
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    FirebaseAuth.getInstance()
                        .sendPasswordResetEmail(binding.editTextTextEmailAddress.text.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    requireContext(),
                                    "Please check your email",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dismiss()
                            }
                        }
                } catch (e : Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
            }

        }

        binding.textViewCancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}