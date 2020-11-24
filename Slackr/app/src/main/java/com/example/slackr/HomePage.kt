package com.example.slackr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage :AppCompatActivity() {

    private var mHomeFragment: HomeFragment? = null
    private var mSearchFragment: SearchFragment? = null
    private var mSettingsFragment: SettingsFragment? = null
    private var mAddFragment: AddFragment? = null
    private var mBottomNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        mHomeFragment = HomeFragment()
        mSearchFragment = SearchFragment()
        mSettingsFragment = SettingsFragment()
        mAddFragment = AddFragment()
        mBottomNavigation = findViewById(R.id.bottomNavigationBar)

        // Set the home page fragment to mFragmentHome
        setFragment(mHomeFragment!!)

        // Set on NavigationItemSelectedListener on mBottomNavigation
        mBottomNavigation!!.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setFragment(mHomeFragment!!)
                R.id.add->setFragment(mAddFragment!!)
                R.id.search->setFragment(mSearchFragment!!)
                R.id.settings->setFragment(mSettingsFragment!!)
            }
            true
        }
    }

    // Function to set a fragment
    private fun setFragment(fragment: Fragment) {
        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.replace(R.id.frame, fragment)
        mTransaction.commit()
    }

    override fun onBackPressed() {
        // Disables the back button
        //Left empty so that users can't go back to the login activity
    }
}