package com.ramgdeveloper.ramglibrary.fragments.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ramgdeveloper.ramglibrary.R
import com.ramgdeveloper.ramglibrary.adapters.ViewPagerAdapter
import com.ramgdeveloper.ramglibrary.fragments.onboarding.ScreenOneFragment
import com.ramgdeveloper.ramglibrary.fragments.onboarding.ScreenThreeFragment
import com.ramgdeveloper.ramglibrary.fragments.onboarding.ScreenTwoFragment

class ViewPagerFragment : Fragment() {
    private lateinit var viewPager2: ViewPager2

   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_pager, container, false)

       viewPager2 = view.findViewById(R.id.viewPager)

       val fragmentList = arrayListOf<Fragment>(
           ScreenOneFragment(),
           ScreenTwoFragment(),
           ScreenThreeFragment()
       )
       val adapter = ViewPagerAdapter(
           fragmentList,
           requireActivity().supportFragmentManager,
           lifecycle
       )
       viewPager2.adapter = adapter
       return view
    }
}