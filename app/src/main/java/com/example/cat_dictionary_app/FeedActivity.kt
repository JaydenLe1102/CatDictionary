package com.example.cat_dictionary_app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.cat_dictionary_app.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

<<<<<<< HEAD:app/src/main/java/com/example/cat_dictionary_app/MainActivity.kt
class MainActivity : AppCompatActivity() {

    lateinit var bottom_navigation : BottomNavigationView

=======
class FeedActivity : AppCompatActivity() {
>>>>>>> 39752ebb31a72679b0615a303930982cd13cc4bb:app/src/main/java/com/example/cat_dictionary_app/FeedActivity.kt
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


}