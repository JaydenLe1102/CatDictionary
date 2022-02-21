package com.example.cat_dictionary_app

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.cat_dictionary_app.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.Headers

class FeedActivity : AppCompatActivity() {
    lateinit var bottom_navigation : BottomNavigationView
    var client = AsyncHttpClient() // put this here since the populateHomeFeed is here

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

    fun populateHomeFeed(query: String = "") {
        // I put this function here because the search function need this function too. You can call this function in the HomeFragment by using  (activity as FeedActivity).populateHomeFeed();
        //TODO: make this function call API with certain query with default not having any query
        val urlToGet = "https://api.thecatapi.com/v1/breeds"

        val headers = RequestHeaders()
        headers.put("x-api-key", HomeFragment.CAT_API_KEY)
//        //headers["Pagination-Count"] = "1"

        val params = RequestParams()
        params.put("attach_breed", query)
        //params.put("x-api-key",  CAT_API_KEY)

        client.get(urlToGet,headers, params, object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(HomeFragment.TAG, "onSuccess!")
                Log.i(HomeFragment.TAG, json.toString())
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.i(HomeFragment.TAG, "onFailure $statusCode")
                Log.e(HomeFragment.TAG, "Fail with reason $response")
            }

        })
    }


    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }


}