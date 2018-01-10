package com.apps.alemjc.prioritytodo

import android.app.Fragment
import android.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v13.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import com.apps.alemjc.prioritytodo.Interfaces.OnListActionListener
import com.apps.alemjc.prioritytodo.Interfaces.OnListIteractionListener
import org.jetbrains.anko.find;
import kotlinx.android.synthetic.main.activity_main.*


/**
 * The Main Activity is used as a middle man in order to communicate in between fragments
 * and updating each fragments when necessary. Fragments could also request the update of order
 * fragments by calling the update methods of this application.
 */
class MainActivity : AppCompatActivity(), OnListIteractionListener {

    internal companion object {
        val FRAGMENT_NUMBER = 2
        val FRAGMENT_TITLES = arrayListOf("Pending", "Done")
    }

    private val pendingFragment = TodosFragment.newInstance(TodosFragment.PENDING_FRAGMENT_TYPE)
    private val doneFragment = TodosFragment.newInstance(TodosFragment.DONE_FRAGMENT_TYPE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)

        val pagerAdapter = MyViewPagerAdapter(fragmentManager)
        viewPager.adapter = pagerAdapter

        tabLayout.setupWithViewPager(viewPager, true)

        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val tab: TabLayout.Tab? = tabLayout.getTabAt(position)
                tab?.select()
            }
        })

    }

    override fun updatePendingTasksView() {
        val pendingListInterator = pendingFragment as OnListActionListener

        pendingListInterator.updateTodos()
    }

    override fun updateDoneTasksView() {
        val doneListInterator = doneFragment as OnListActionListener

        doneListInterator.updateTodos()
    }

    inner class MyViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> pendingFragment
                else -> doneFragment
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
