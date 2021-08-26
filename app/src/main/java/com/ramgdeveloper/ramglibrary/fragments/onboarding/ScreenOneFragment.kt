package com.ramgdeveloper.ramglibrary.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentScreenOneBinding

class ScreenOneFragment : Fragment() {
    private lateinit var binding: FragmentScreenOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_screen_one, container, false)
        //binding = FragmentScreenOneBinding.inflate(inflater, container, false)
        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
        val btn = view.findViewById<Button>(R.id.buttonNext2)
        btn.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return view
    }
}