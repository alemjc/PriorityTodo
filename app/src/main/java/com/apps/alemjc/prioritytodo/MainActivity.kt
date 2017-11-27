package com.apps.alemjc.prioritytodo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal companion object {
       val FRAGMENT_NUMBER = 2
       val FRAGMENT_TITLES = arrayListOf("Pending", "Done")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pagerAdapter = MyViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

    }

    class MyViewPagerAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager){
        override fun getItem(position: Int): Fragment {
            if(position == 0) {
                return TodosFragment.newInstance(TodosFragment.PENDING_FRAGMENT_TYPE)
            }
            else{
                return TodosFragment.newInstance(TodosFragment.DONE_FRAGMENT_TYPE)
            }

        }

        override fun getCount(): Int {
            return FRAGMENT_NUMBER
        }

        override fun getPageTitle(position: Int): CharSequence {
            return FRAGMENT_TITLES[position]
        }
    }
}
