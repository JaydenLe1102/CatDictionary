package com.example.cat_dictionary_app.data

import android.os.Parcelable
import android.util.Log
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.cat_dictionary_app.R
import com.example.cat_dictionary_app.fragments.HomeFragment
import com.example.cat_dictionary_app.fragments.SearchFragment
import kotlinx.parcelize.Parcelize
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlinx.coroutines.GlobalScope

@Parcelize
data class Breed (
    val id: String,
    val name: String,
    val description: String,
    val temperament: String,
    val life_span: String,
    val origin: String,
//    val wikipedia_url: String,
    val adaptability: Int,
    val affection_level: Int,
    val child_friendly: Int,
    val dog_friendly: Int,
    val energy_level: Int,
    val grooming: Int,
    val health_issues: Int,
    val intelligence: Int,
    val shedding_level: Int,
    val social_needs: Int,
    val stranger_friendly: Int,
    val vocalisation: Int,
    val imageurl: String

    ):Parcelable {

        companion object{
            val TAG = "Breed"

            fun fromJsonArray(breedjsonarray: JSONArray): ArrayList<Breed>
            {
                val breeds = ArrayList<Breed>()
                for ( i in 0 until breedjsonarray.length())
                {
                    val breedJson = breedjsonarray.getJSONObject(i)
                    //Log.i(TAG, breedJson.toString())
                    // imageURL has a default URL in case the breed jsonObj doesn't have
                    // key "image" in itself or key "url" in its image jsonObj
                    var imageURL = "https://wtwp.com/wp-content/uploads/2015/06/placeholder-image.png"
                    if (breedJson.has("image") &&
                        breedJson.getJSONObject("image").has("url")) {
                        imageURL = getImageURL(breedJson.getJSONObject("image"))
                    }

                    breeds.add(
                        Breed(
                            breedJson.getString("id"),
                            breedJson.getString("name"),
                            breedJson.getString("description"),
                            breedJson.getString("temperament"),
                            breedJson.getString("life_span"),
                            breedJson.getString("origin"),
//                            breedJson.getString("wikipedia_url"),
                            breedJson.getInt("adaptability"),
                            breedJson.getInt("affection_level"),
                            breedJson.getInt("child_friendly"),
                            breedJson.getInt("dog_friendly"),
                            breedJson.getInt("energy_level"),
                            breedJson.getInt("grooming"),
                            breedJson.getInt("health_issues"),
                            breedJson.getInt("intelligence"),
                            breedJson.getInt("shedding_level"),
                            breedJson.getInt("social_needs"),
                            breedJson.getInt("stranger_friendly"),
                            breedJson.getInt("vocalisation"),
                            imageURL
                        )
                    )
                }
                return breeds
            }

            fun fromSearchBreedArray(breedjsonarray: JSONArray): ArrayList<Breed>
            {
                val breeds = ArrayList<Breed>()
                Log.i("BreedSearch", breedjsonarray.toString())
                for (i in 0 until breedjsonarray.length())
                {
                    val breedJsonI = breedjsonarray.getJSONObject(i)
                    var breedJson: JSONObject? = null
                    var imageURL = "https://wtwp.com/wp-content/uploads/2015/06/placeholder-image.png"
                    if (breedJsonI.has("reference_image_id"))
                    {
                        Log.i("BreedSearch", breedJsonI.getString("reference_image_id"))
                        breedJson = queryForBreedWithImageid(breedJsonI.getString("reference_image_id"))

                        Log.i("BreedSearch", breedJson.toString())
                    }


                    if (breedJson!= null)
                    {
                        if (breedJson.has("image") &&
                            breedJson.getJSONObject("image").has("url")) {
                            imageURL = getImageURL(breedJson.getJSONObject("image"))
                        }

                        Log.i("BreedSearch", "imgae url = " + imageURL)

                        breeds.add(
                            Breed(
                                breedJson.getString("id"),
                                breedJson.getString("name"),
                                breedJson.getString("description"),
                                breedJson.getString("temperament"),
                                breedJson.getString("life_span"),
                                breedJson.getString("origin"),
//                            breedJson.getString("wikipedia_url"),
                                breedJson.getInt("adaptability"),
                                breedJson.getInt("affection_level"),
                                breedJson.getInt("child_friendly"),
                                breedJson.getInt("dog_friendly"),
                                breedJson.getInt("energy_level"),
                                breedJson.getInt("grooming"),
                                breedJson.getInt("health_issues"),
                                breedJson.getInt("intelligence"),
                                breedJson.getInt("shedding_level"),
                                breedJson.getInt("social_needs"),
                                breedJson.getInt("stranger_friendly"),
                                breedJson.getInt("vocalisation"),
                                imageURL
                            )
                        )
                    }
//                    breedJson.getJSONObject("image").has("url"))
//                    imageURL = getImageURL(breedJson.getJSONObject("image"))


                }
                return breeds
            }


            fun queryForBreedWithImageid(imageId: String): JSONObject?
            {
                //Log.i("BreedSearch", imageId)

                var re: JSONObject? = null
                val urlImage = "https://api.thecatapi.com/v1/images/$imageId/"
                val client = AsyncHttpClient()

                val headers = RequestHeaders()
                headers.put("x-api-key", HomeFragment.CAT_API_KEY)

                val params = RequestParams()
//                params.put("image_id", imageId)

                client.get(urlImage, headers, params, object : JsonHttpResponseHandler(){
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.i("BreedSearch", "onFailure $statusCode")
                        Log.e("BreedSearch", "Fail with reason $response")
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                        Log.i("BreedSearch", "onSuccess" )
                        Log.i("BreedSearch", json.toString())
                        re = json.jsonObject
                    }

                })

                return re
            }


            fun getImageURL(jsonObject: JSONObject):String{
                return jsonObject.getString("url")
            }

        }
}