package com.example.cat_dictionary_app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class FeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item ->

            // Find out which item was clicked
            when (item.itemId) {
                R.id.action_home->{
                    // TODO: Navigate to the Home Screen
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

            // Return true to say that we've handled this user interaction on the item
            true
        }
    }
}