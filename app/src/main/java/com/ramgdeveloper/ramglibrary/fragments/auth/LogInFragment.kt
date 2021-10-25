package com.ramgdeveloper.ramglibrary.fragments.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentLogInBinding
import com.ramgdeveloper.ramglibrary.others.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LogInFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        //Sign in with Email and Password
        binding.logInButton.setOnClickListener { it ->
            Utils.hideKeyboard(it)
            logInWithEmailAndPassword()
        }

        binding.forgotpasswordTV.setOnClickListener {
            val forgotPassword = ForgotPasswordFragment()
            forgotPassword.show(childFragmentManager, "dialog_reset_password")
        }

        binding.signUpTV.setOnClickListener {
            findNavController().navigate(R.id.logIn_to_registerFragment)
        }

        //Log in with Google
        binding.googleButton.setOnClickListener {
            binding.googleProgress.visibility = VISIBLE
            if (loggedInState()){
                val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.webclient_id))
                    .requestEmail()
                    .build()

                val signInClient = GoogleSignIn.getClient(requireActivity(), options)
                signInClient.signInIntent.also {
                    startActivityForResult(it, Utils.REQUEST_ID)
                    binding.googleProgress.visibility = INVISIBLE
                }
            }
        }
        return binding.root
    }

    //Login with email and password
    private fun logInWithEmailAndPassword() {
        val email: String = binding.emailLogInET.editText?.text.toString()
        val password: String = binding.passwordLogInET.editText?.text.toString()

        when {
            binding.emailLogInET.editText?.text.toString().isEmpty() -> {
                binding.emailLogInET.editText?.error = "Email required"
            }
            binding.passwordLogInET.editText?.text.toString().isEmpty() -> {
                binding.passwordLogInET.editText?.error = "Password required"
            }
            else -> {
                binding.loginProgressBar.visibility = VISIBLE
                binding.logInButton.isEnabled = false

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            firebaseAuth.signInWithEmailAndPassword(email, password).await()

                            withContext(Dispatchers.Main) {
                                binding.loginProgressBar.visibility = GONE
                                binding.logInButton.isEnabled = true
                                binding.emailLogInET.editText?.setText("")
                                binding.passwordLogInET.editText?.setText("")

                                val firebaseUser = firebaseAuth.currentUser
                                if (firebaseUser!!.isEmailVerified) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Logged in Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    findNavController().navigate(R.id.loginFragment_to_homeFragment)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Please verify your email first",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                                binding.loginProgressBar.visibility = GONE
                                binding.logInButton.isEnabled = true
                                binding.emailLogInET.editText?.setText("")
                                binding.passwordLogInET.editText?.setText("")
                            }
                        }
                    }
                }
            }
        }

    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount?) {
        val credentials = GoogleAuthProvider.getCredential(account?.idToken, null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                firebaseAuth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Logged in Successfully", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.loginFragment_to_homeFragment)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (requestCode == Utils.REQUEST_ID && data != null) {
                    val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
                    account.let {
                        googleAuthForFirebase(it)
                    }
                }
            } catch (e : Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(), "Not Registered", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun loggedInState(): Boolean {
        if (firebaseAuth.currentUser == null){
            return true
        }
        return false
    }

}