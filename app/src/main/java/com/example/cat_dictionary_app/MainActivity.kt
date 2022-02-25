package com.example.cat_dictionary_app

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.cat_dictionary_app.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity() {

    lateinit var bottom_navigation : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager
        bottom_navigation = findViewById(R.id.bottom_navigation)
        bottom_navigation.setOnItemSelectedListener {
            item ->

            var fragmentToShow: Fragment? = null
            // Find out which item was clicked
            when (item.itemId) {
                R.id.action_home->{
                    fragmentToShow = HomeFragment()
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.action_search->{
                    // TODO: Navigate to the Search Screen
                    Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                }
                R.id.action_watch->{
                    // TODO: Navigate to the Watch Screen
                    Toast.makeText(this, "Watch", Toast.LENGTH_SHORT).show()
                }
                R.id.action_index->{
                    // TODO: Navigate to the Index Screen
                    Toast.makeText(this, "Index", Toast.LENGTH_SHORT).show()
                }
            }

            if (fragmentToShow != null) {
                // call commit so that things happen
                // replace the container in the layout file with the fragment that we want to show
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }

            // Return true to say that we've handled this user interaction on the item
            true
        }

        // Set default fragment to show if none of the button on bottom navigation bar is clicked
        bottom_navigation.selectedItemId = R.id.action_home
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    companion object {
        val TAG = "MainActivity"
    }
}