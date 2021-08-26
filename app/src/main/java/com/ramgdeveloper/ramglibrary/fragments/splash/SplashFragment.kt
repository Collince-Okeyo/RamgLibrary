package com.ramgdeveloper.ramglibrary.fragments.splash

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentSplash2Binding

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplash2Binding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSplash2Binding.inflate(inflater, container, false)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler().postDelayed({
            if (user != null){
                findNavController().navigate(R.id.splash_to_home)
            }else{
                findNavController().navigate(R.id.splash_to_login)
            }
        }, 2000)

        return binding.root
    }
}