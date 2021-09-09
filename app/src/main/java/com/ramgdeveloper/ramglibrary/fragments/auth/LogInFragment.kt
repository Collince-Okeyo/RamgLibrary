package com.ramgdeveloper.ramglibrary.fragments.auth

import android.content.Intent
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentLogInBinding
import com.ramgdeveloper.ramglibrary.others.Utils

private const val TAG = "LogInFragment"

class LogInFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: FragmentLogInBinding

    private
    companion object {
        private const val RC_SIGN_IN = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //.requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.googleButton.setOnClickListener {
            signIn()
        }

        /*  val email: String = binding.emailLogInET.editText?.text.toString()
          val password: String  = binding.passwordLogInET.editText?.text.toString()*/

        //Signin with Email and Passwrd
        binding.logInButton.setOnClickListener { it ->
            Utils.hideKeyboard(it)

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

                    firebaseAuth.signInWithEmailAndPassword(
                        binding.emailLogInET.editText?.text.toString(),
                        binding.passwordLogInET.editText?.text.toString()
                    ).addOnCompleteListener {
                        binding.loginProgressBar.visibility = GONE
                        binding.logInButton.isEnabled = true

                        binding.emailLogInET.editText?.setText("")
                        binding.passwordLogInET.editText?.setText("")

                        val firebaseUser = firebaseAuth.currentUser!!
                        if (firebaseUser.isEmailVerified) {
                            findNavController().navigate(R.id.loginFragment_to_homeFragment)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Please verify your email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }.addOnFailureListener {
                        binding.loginProgressBar.visibility = GONE
                        binding.logInButton.isEnabled = true

                        binding.emailLogInET.editText?.setText("")
                        binding.passwordLogInET.editText?.setText("")

                        Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        binding.forgotpasswordTV.setOnClickListener {
            val forgotPassword = ForgotPasswordFragment()
            forgotPassword.show(childFragmentManager, "dialog_reset_password")
        }

        binding.signUpTV.setOnClickListener {
            findNavController().navigate(R.id.logIn_to_registerFragment)
        }
        return binding.root
    }

    //SignIn method
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("LogInFragment", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("LogInFragment", exception.toString())
                }
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LogInFragment", "signInWithCredential:success")
                    findNavController().navigate(R.id.loginFragment_to_homeFragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LogInFragment", "signInWithCredential:failure", task.exception)
                }
            }
    }
}