package com.ramgdeveloper.ramglibrary.fragments.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.ramgdeveloper.ramglibrary.R

private const val TAG = "SplashFragment"

class SplashFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash2, container, false)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler().postDelayed({
            if (user != null && onBoardingFinished()) {
                findNavController().navigate(R.id.splash_to_home)
                Log.d(TAG, "user not null")
            } else if (onBoardingFinished() && user == null) {
                findNavController().navigate(R.id.splash_to_login)
                Log.d(TAG, "user null and onboarding finished")
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
                Log.d(TAG, "new install")
            }
        }, 2000)

        return view
    }

    private fun onBoardingFinished(): Boolean {
        val sharePreferences =
            requireContext().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharePreferences.getBoolean("Finished", false)
    }
}