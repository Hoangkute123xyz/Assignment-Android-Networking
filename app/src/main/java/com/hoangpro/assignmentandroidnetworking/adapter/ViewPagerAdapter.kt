package com.hoangpro.assignmentandroidnetworking.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior){
    val fragmentList = ArrayList<Fragment>()
    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int =fragmentList.size

    fun addFragment(fragment:Fragment){
        fragmentList.add(fragment)
    }
}