package com.ramgdeveloper.ramglibrary.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.databinding.FragmentScreenOneBinding
import com.ramgdeveloper.ramglibrary.databinding.FragmentScreenTwoBinding

class ScreenTwoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_screen_two, container, false)
        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
        val btn = view.findViewById<Button>(R.id.buttonNext2)
        btn.setOnClickListener {
            viewPager?.currentItem = 2
        }

        return view
    }
}