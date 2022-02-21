package com.example.cat_dictionary_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.cat_dictionary_app.BuildConfig
import com.example.cat_dictionary_app.FeedActivity
import com.example.cat_dictionary_app.R
import okhttp3.Headers


class HomeFragment : Fragment() {

    lateinit var postsRecyclerView : RecyclerView

    // onCreateView of Fragment is similar to onCreate of Activity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This is where we set up our views and clicklisteners

        view.findViewById<RecyclerView>(R.id.postsRecyclerView)

        Log.i(TAG, "Finished setting up")

        // Steps to populate RecyclerView
        // 1. Create a layout for each row in RecyclerView
        // 2. Create data source for each row (this is the Post class)
        // 3. Create adapter that will bridge data and row layout
        // 4. Set adapter on RecyclerView
        // 5. Set layout manager on RecyclerView

        // Call populateHomeFeed once var client is initialized
        (activity as FeedActivity).populateHomeFeed();
    }



    companion object {
        val TAG = "HomeFragment"
        const val CAT_API_KEY = BuildConfig.CONSUMER_KEY.toString()// See more in apikey.properties
    }
}