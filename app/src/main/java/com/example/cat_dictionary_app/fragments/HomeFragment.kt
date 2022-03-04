package com.example.cat_dictionary_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.cat_dictionary_app.BuildConfig
import com.example.cat_dictionary_app.R
import com.example.cat_dictionary_app.data.Breed
import okhttp3.Headers
import org.json.JSONException


open class HomeFragment : Fragment() {

    private lateinit var postsRecyclerView : RecyclerView
    lateinit var breedSummaryPostAdapter : BreedSummaryPostAdapter
    val client = AsyncHttpClient()
    val breeds = mutableListOf<Breed>()
    private lateinit var swipeContainer: SwipeRefreshLayout

    // onCreateView of Fragment is similar to onCreate of Activity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val listbreeds = (activity as MainActivity).getBreedList()
//        if (!listbreeds.isEmpty()){
//            breeds.clear()
//            breeds.addAll(listbreeds)
//        }

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This is where we set up our views and clicklisteners

        // Lookup the swipe container view
        swipeContainer = view.findViewById(R.id.swipeContainerGallery)
        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            Log.i(TAG, "Refreshing timeline")

            refreshTimeline()
        }

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

        postsRecyclerView = view.findViewById(R.id.galleryRecyclerView)
        // Steps to populate RecyclerView
        // 1. Create a layout for each row in RecyclerView
        // 2. Create data source for each row (this is the Breed class)
        // 3. Create adapter that will bridge data and row layout
        // 4. Set adapter on RecyclerView
        // 5. Set layout manager on RecyclerView
        breedSummaryPostAdapter = BreedSummaryPostAdapter(requireContext(), breeds)
        postsRecyclerView.adapter = breedSummaryPostAdapter
        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Call populateHomeFeed once var client is initialized

        populateHomeFeed()

    }

    private fun refreshTimeline() {
        breeds.shuffle()
        breedSummaryPostAdapter.notifyDataSetChanged()
        // Now we call setRefreshing(false) to signal refresh has finished
        swipeContainer.setRefreshing(false)
    }

    public open fun populateHomeFeed(query: Int = 0) {
        // I put this function here because the search function need this function too.
        // You can call this function in the HomeFragment by using
        // (activity as MainActivity).populateHomeFeed();
        //TODO: make this function call API with certain query with default not having any query
        val urlToGet = "https://api.thecatapi.com/v1/breeds"

        val headers = RequestHeaders()
        headers.put("x-api-key", CAT_API_KEY)

        val params = RequestParams()
        params.put("attach_breed", query)

        client.get(urlToGet,headers, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                // Add try-catch here for because Breed.fromJsonArray might throw
                // JsonException when the API gets updated with new header/query names
                // and we don't want our app to crash in those cases
                try {
                    breeds.addAll(Breed.fromJsonArray(json.jsonArray))
                    breeds.shuffle()
                    breedSummaryPostAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e(TAG, "Encountered exception $e")
                    Log.i(TAG, "json is: $json.jsonArray.getJSONObject(0)")
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.i(TAG, "onFailure $statusCode")
                Log.e(TAG, "Fail with reason $response")
            }

        })
    }



    companion object {
        const val TAG = "HomeFragment"
        const val CAT_API_KEY = BuildConfig.CONSUMER_KEY // See more in apikey.properties
    }
}