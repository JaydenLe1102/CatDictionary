package com.example.cat_dictionary_app

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.cat_dictionary_app.data.Breed
import com.example.cat_dictionary_app.fragments.GalleryFragment
import com.example.cat_dictionary_app.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.Headers
import org.json.JSONException

import com.example.cat_dictionary_app.fragments.SearchFragment


class MainActivity : AppCompatActivity() {

    lateinit var bottom_navigation : BottomNavigationView
    lateinit var searchView: SearchView
    var fragmentToShow: Fragment? = null
    var fragmentManager: FragmentManager = supportFragmentManager
    val client = AsyncHttpClient()
    val breeds = mutableListOf<Breed>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottom_navigation = findViewById(R.id.bottom_navigation)
        bottom_navigation.setOnItemSelectedListener {
            item ->

            // Find out which item was clicked
            when (item.itemId) {
                R.id.action_home->{
                    fragmentToShow = HomeFragment()
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.action_watch->{
                    // TODO: Navigate to the Watch Screen
                    Toast.makeText(this, "Watch", Toast.LENGTH_SHORT).show()
                }
                R.id.action_gallery->{
                    fragmentToShow = GalleryFragment()
                    Toast.makeText(this, "Gallery", Toast.LENGTH_SHORT).show()
                }
            }

            if (fragmentToShow != null) {
                // call commit so that things happen
                // replace the container in the layout file with the fragment that we want to show
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow!!).commit()
            }

            // Return true to say that we've handled this user interaction on the item
            true
        }

        // Set default fragment to show if none of the button on bottom navigation bar is clicked
        bottom_navigation.selectedItemId = R.id.action_home
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        var searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    search(query)
                }

                //reset searchview
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchView.setIconified(true)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return true
    }



    private fun search(query: String) {
        // I put this function here because the search function need this function too.
        // You can call this function in the HomeFragment by using
        // (activity as MainActivity).populateHomeFeed();
        //TODO: make this function call API with certain query with default not having any query
        val urlToGet = "https://api.thecatapi.com/v1/breeds/search"

        val headers = RequestHeaders()
        headers.put("x-api-key", HomeFragment.CAT_API_KEY)

        val params = RequestParams()
        params.put("q", query)

        client.get(urlToGet,headers, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                // Add try-catch here for because Breed.fromJsonArray might throw
                // JsonException when the API gets updated with new header/query names
                // and we don't want our app to crash in those cases
                try {
                    breeds.clear()
                    Breed.fromSearchBreedArray(json.jsonArray)
                    breeds.addAll(Breed.fromJsonArray(json.jsonArray))
                    fragmentToShow = SearchFragment()
                    bottom_navigation.selectedItemId = R.id.action_search
                    if (fragmentToShow != null) {
                        // call commit so that things happen
                        // replace the container in the layout file with the fragment that we want to show
                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow!!).commit()
                    }
                    //breedSummaryPostAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e(HomeFragment.TAG, "Encountered exception $e")
                    Log.i(HomeFragment.TAG, "json is: $json.jsonArray.getJSONObject(0)")
                }

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

    public fun getBreedList(): MutableList<Breed> {
        return breeds
    }

    companion object {
        val TAG = "MainActivity"
    }
}