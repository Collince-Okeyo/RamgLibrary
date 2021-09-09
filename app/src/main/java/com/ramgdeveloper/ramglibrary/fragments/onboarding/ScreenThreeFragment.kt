package com.ramgdeveloper.ramglibrary.fragments.onboarding

import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentScreenThreeBinding

class ScreenThreeFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentScreenThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        binding = FragmentScreenThreeBinding.inflate(inflater, container, false)
        binding.buttonFinish.setOnClickListener {
            if (user == null){
                findNavController().navigate(R.id.action_viewPagerFragment_to_logInFragment)
            }else{
                findNavController().navigate(R.id.viewPagerFragment_to_homeFragment)
            }
            onBoaringFinished()
        }
        return binding.root
    }
    private fun onBoaringFinished(){
        val sharedPreferences = requireContext().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}