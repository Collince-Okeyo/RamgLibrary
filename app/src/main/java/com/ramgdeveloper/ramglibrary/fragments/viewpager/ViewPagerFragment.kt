package com.ramgdeveloper.ramglibrary.fragments.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramgdeveloper.ramglibrary.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        return binding.root
    }
}