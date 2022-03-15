package com.example.cat_dictionary_app

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.SearchAutoComplete
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


//import com.example.cat_dictionary_app.fragments.SearchFragment


class MainActivity : AppCompatActivity() {
    lateinit var bottom_navigation : BottomNavigationView
    lateinit var searchView: SearchView
    var checksearchView =  false
    lateinit var searchAutoComplete: SearchAutoComplete
    var fragmentToShow: Fragment? = null
    var fragmentManager: FragmentManager = supportFragmentManager
    val client = AsyncHttpClient()
    val breeds = mutableListOf<Breed>()
    var breedNames = ArrayList<String>()

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
                    if (checksearchView == true)
                    {
                        searchView.clearFocus()
                        searchView.setQuery("", false)
                        searchView.setIconified(true)
                    }
                    breeds.clear()
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
        //Log.i(TAG, breedNames.toString())
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        var searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        checksearchView = true

        //val searchText: TextView = searchView.findViewById(androidx.appcompat.R.id.search_src_text)

        val temp =
            Resources.getSystem().getIdentifier("search_src_text",
                "id", "android")

        if (temp != null)
        {
            Log.i(TAG, temp.toString())

            Log.i(TAG, "hello")
            //val mSearchSrcTextView = temp as SearchAutoComplete
            //mSearchSrcTextView.threshold = 1
        }



        //set up cursor adapter
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        val cursorAdapter = SimpleCursorAdapter(this, R.layout.suggestionitem, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)



        searchView.suggestionsAdapter = cursorAdapter

        //get searchView autocomplete object
        //var searchAutoCompleteAdapter = searchView.suggestionsAdapter



        searchView.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            @SuppressLint("Range")
            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView.setQuery(selection, false)
                hideKeyboard()
                //reset searchview
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchView.setIconified(true)

                search(selection)



                return true
            }
        })



        //Below event is triggered when submit search query
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    hideKeyboard()

                    search(query)
                }

                //reset searchview
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchView.setIconified(true)

                breeds.clear()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                if (newText != null) {
                    Log.i("Autocomplete", newText)
                }
                newText?.let {
                    breedNames.forEachIndexed { index, suggestion ->
                        if (suggestion.lowercase().contains(newText, true))
                        {
                            Log.i("Autocomplete", suggestion)
                            cursor.addRow(arrayOf(index, suggestion))


                        }
                    }
                }


                cursorAdapter.changeCursor(cursor)
                cursorAdapter.notifyDataSetChanged()


                return true
                //return false
            }

        })



        return true
    }



    private fun search(key:String, query: Int = 0) {
        // I put this function here because the search function need this function too.
        // You can call this function in the HomeFragment by using
        // (activity as MainActivity).populateHomeFeed();
        //TODO: make this function call API with certain query with default not having any query
        val urlToGet = "https://api.thecatapi.com/v1/breeds"

        val headers = RequestHeaders()
        headers.put("x-api-key", HomeFragment.CAT_API_KEY)

        val params = RequestParams()
        params.put("attach_breed", query)

        client.get(urlToGet,headers, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                // Add try-catch here for because Breed.fromJsonArray might throw
                // JsonException when the API gets updated with new header/query names
                // and we don't want our app to crash in those cases
                try {

                    breeds.clear()
                    breeds.addAll(Breed.filterWithKey(json.jsonArray, key))
                    fragmentToShow = HomeFragment()
                    //bottom_navigation.selectedItemId = R.id.action_home
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

    fun setBreedNameList(breedsName: ArrayList<String>)
    {
        breedNames = breedsName
    }


    fun Context.hideKeyboard() {
        val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        val viewGroup =
            (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        inputMethodManager.hideSoftInputFromWindow(viewGroup.windowToken, 0)
    }


    companion object {
        val TAG = "MainActivity"
    }
}