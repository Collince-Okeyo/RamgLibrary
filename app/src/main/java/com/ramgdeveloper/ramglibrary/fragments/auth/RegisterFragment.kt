package com.ramgdeveloper.ramglibrary.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.VisibleForTesting
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
                    binding.progressRegister.visibility = VISIBLE
                    binding.registerButton.isEnabled = false
                    
                    firebaseAuth.createUserWithEmailAndPassword(binding.emailSignUpET.editText?.text.toString(), 
                        binding.passwordSignUpET.editText?.text.toString()).addOnSuccessListener {
                        Toast.makeText(requireContext(), "Acount created sucessfully", Toast.LENGTH_SHORT).show()

                        binding.progressRegister.visibility = GONE
                        binding.registerButton.isEnabled = true

                        val firebaseUser = firebaseAuth.currentUser!!
                        firebaseUser.sendEmailVerification().addOnSuccessListener {
                            Toast.makeText(requireContext(),
                                "Email verification link has been sent to"+
                                        binding.emailSignUpET.editText?.text, Toast.LENGTH_SHORT).show()

                            binding.firstNameET.editText?.setText("")
                            binding.lastNameET.editText?.setText("")
                            binding.emailSignUpET.editText?.setText("")
                            binding.passwordSignUpET.editText?.setText("")
                            binding.phoneET.editText?.setText("")

                            findNavController().navigate(R.id.action_registerFragment_to_logInFragment)
                        }.addOnFailureListener {
                            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()

                            binding.progressRegister.visibility = GONE
                            binding.registerButton.isEnabled = true

                            binding.firstNameET.editText?.setText("")
                            binding.lastNameET.editText?.setText("")
                            binding.emailSignUpET.editText?.setText("")
                            binding.passwordSignUpET.editText?.setText("")
                            binding.phoneET.editText?.setText("")
                        }
                    }
                }
            }
        }

        binding.logInTV.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_logInFragment)
        }
        return binding.root
    }
}