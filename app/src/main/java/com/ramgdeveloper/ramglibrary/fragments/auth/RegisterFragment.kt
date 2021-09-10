package com.ramgdeveloper.ramglibrary.fragments.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.data.Users
import com.ramgdeveloper.ramglibrary.databinding.FragmentRegisterBinding
import com.ramgdeveloper.ramglibrary.others.Utils

private const val TAG = "RegisterFragment"

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userID: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        binding.registerButton.setOnClickListener {
            Utils.hideKeyboard(it)
            when {
                binding.firstNameET.editText?.text.toString().isEmpty() -> {
                    binding.firstNameET.editText?.error = "Enter First Name"
                }
                binding.lastNameET.editText?.text.toString().isEmpty() -> {
                    binding.firstNameET.editText?.error = "Enter Last Name"
                }
                binding.emailSignUpET.editText?.text.toString().isEmpty() -> {
                    binding.emailSignUpET.editText?.error = "Enter Last Email"
                }
                binding.passwordSignUpET.editText?.text.toString().isEmpty() -> {
                    binding.passwordSignUpET.editText?.error = "Enter Last Password"
                }
                binding.phoneET.editText?.text.toString().isEmpty() -> {
                    binding.phoneET.editText?.error = "Enter Last Number"
                }
                binding.passwordSignUpET.editText?.text.toString().length < 8 -> {
                    binding.firstNameET.editText?.error = "Short Password"
                }
                else -> {
                    binding.progressRegister.visibility = VISIBLE
                    binding.registerButton.isEnabled = false

                    firebaseAuth.createUserWithEmailAndPassword(
                        binding.emailSignUpET.editText?.text.toString(),
                        binding.passwordSignUpET.editText?.text.toString()
                    ).addOnCompleteListener {
                        Toast.makeText(
                            requireContext(),
                            "Acount created sucessfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        binding.progressRegister.visibility = GONE
                        binding.registerButton.isEnabled = true

                        Log.d(TAG, "onCreateView: User created")
                        val firebaseUser = firebaseAuth.currentUser!!
                        firebaseUser.sendEmailVerification().addOnSuccessListener {
                            userID = firebaseAuth.currentUser?.uid.toString()

                            saveUserDetails(
                                firstName = binding.firstNameET.editText?.text.toString(),
                                lastName = binding.lastNameET.editText?.text.toString(),
                                email = binding.emailSignUpET.editText?.text.toString(),
                                phoneNumber = binding.phoneET.editText?.text.toString()
                            )
                            Toast.makeText(
                                requireContext(),
                                "Email verification link has been sent to" +
                                        binding.emailSignUpET.editText?.text, Toast.LENGTH_LONG
                            ).show()

                            binding.firstNameET.editText?.setText("")
                            binding.lastNameET.editText?.setText("")
                            binding.emailSignUpET.editText?.setText("")
                            binding.passwordSignUpET.editText?.setText("")
                            binding.phoneET.editText?.setText("")

                            findNavController().navigate(R.id.action_registerFragment_to_logInFragment)
                            Log.d(TAG, "onCreateView: Verification sent")
                        }.addOnFailureListener {
                            Toast.makeText(
                                requireContext(),
                                it.localizedMessage,
                                Toast.LENGTH_LONG
                            ).show()

                            binding.progressRegister.visibility = GONE
                            binding.registerButton.isEnabled = true

                            binding.firstNameET.editText?.setText("")
                            binding.lastNameET.editText?.setText("")
                            binding.emailSignUpET.editText?.setText("")
                            binding.passwordSignUpET.editText?.setText("")
                            binding.phoneET.editText?.setText("")
                            Log.d(TAG, "onCreateView: Failed")
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

    private fun saveUserDetails(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String
    ) {
        val user = Users(firstName, lastName, email, phoneNumber)
        databaseReference.child(userID).setValue(user)
    }
}