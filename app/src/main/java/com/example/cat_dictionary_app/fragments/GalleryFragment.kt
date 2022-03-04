package com.example.cat_dictionary_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.cat_dictionary_app.BuildConfig
import com.example.cat_dictionary_app.R
import com.example.cat_dictionary_app.data.GalleryImage
import okhttp3.Headers
import org.json.JSONException


open class GalleryFragment : Fragment() {

    private lateinit var galleryRecyclerView : RecyclerView
    lateinit var galleryAdapter: GalleryAdapter

    val client = AsyncHttpClient()
    val galleryImages = mutableListOf<GalleryImage>()
    private lateinit var swipeContainerGallery: SwipeRefreshLayout

    // onCreateView of Fragment is similar to onCreate of Activity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This is where we set up our views and clicklisteners

        // Lookup the swipe container view
        swipeContainerGallery = view.findViewById(R.id.swipeContainerGallery)
        // Setup refresh listener which triggers new data loading

        swipeContainerGallery.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            Log.i(TAG, "Refreshing Gallery")

            populateGallery()
        }

        // Configure the refreshing colors
        swipeContainerGallery.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

        galleryRecyclerView = view.findViewById(R.id.galleryRecyclerView)
        // Steps to populate RecyclerView
        // 1. Create a layout for each row in RecyclerView
        // 2. Create data source for each row
        // 3. Create adapter that will bridge data and row layout
        // 4. Set adapter on RecyclerView
        // 5. Set layout manager on RecyclerView
        galleryAdapter = GalleryAdapter(requireContext(), galleryImages)
        galleryRecyclerView.adapter = galleryAdapter
//        galleryRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
//        galleryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        galleryRecyclerView.layoutManager = GridLayoutManager(requireContext(),2,LinearLayoutManager.VERTICAL,false)

        Log.i(TAG, "Finished setting up galleryAdapter")

        // Call populateGallery once var client is initialized
        populateGallery()

        Log.i(TAG, "Finished populating gallery")
    }

    fun populateGallery() {
        val urlToGet = "https://api.thecatapi.com/v1/images/search"

        val headers = RequestHeaders()
        headers.put("x-api-key", CAT_API_KEY)

        val params = RequestParams()
        params.put("size", "full")
        params.put("order", "random")
        params.put("limit", "30")

        client.get(urlToGet,headers, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                // Add try-catch here for because GalleryImage.fromJsonArray might throw
                // JsonException when the API gets updated with new header/query names
                // and we don't want our app to crash in those cases
                try {
                    val firstJSONObj = json.jsonArray.getJSONObject(0)
                    Log.i(TAG, "json is: $firstJSONObj")

                    galleryAdapter.clear()
                    galleryImages.addAll(GalleryImage.fromJsonArray(json.jsonArray))
                    galleryAdapter.notifyDataSetChanged()
                    swipeContainerGallery.setRefreshing(false)
                } catch (e: JSONException) {
                    Log.e(TAG, "Encountered exception $e")
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
        const val TAG = "GalleryFragment"
        const val CAT_API_KEY = BuildConfig.CONSUMER_KEY // See more in apikey.properties
    }
}