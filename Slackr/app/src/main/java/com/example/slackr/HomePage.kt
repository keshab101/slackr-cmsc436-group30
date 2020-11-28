package com.example.slackr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.slackr.fragments.AddFragment
import com.example.slackr.fragments.HomeFragment
import com.example.slackr.fragments.SearchFragment
import com.example.slackr.fragments.SettingsFragment
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
        supportActionBar?.title = "Dashboard"

        // Set on NavigationItemSelectedListener on mBottomNavigation
        mBottomNavigation!!.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home-> {
                    supportActionBar?.title = "Dashboard"
                    setFragment(mHomeFragment!!)
                }
                R.id.add-> {
                    supportActionBar?.title = "Add"
                    setFragment(mAddFragment!!)
                }
                R.id.search-> {
                    supportActionBar?.title = "Search"
                    setFragment(mSearchFragment!!)
                }
                R.id.settings-> {
                    supportActionBar?.title = "Profile"
                    setFragment(mSettingsFragment!!)
                }
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

    companion object {
        const val USER_ID = "student_id"
    }
}