package com.example.spideysense

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import android.R




class ComicPagerAdapter(val fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ComicFragment1() //ChildFragment1 at position 0
            1 -> return ComicFragment2() //ChildFragment2 at position 1
        }

        return ComicFragment1()
    }

    override fun getCount(): Int {
        return 2 //three fragments
    }

    fun getFragmentByPositionUsingTag(viewId: Int, position: Int): Fragment? {

        return fm.findFragmentByTag("android:switcher:" + viewId + ":" + position)
    }
}