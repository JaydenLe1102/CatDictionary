package com.example.cat_dictionary_app.data

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
import org.json.JSONObject

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

            fun getImageURL(jsonObject: JSONObject):String{
                return jsonObject.getString("url")
            }

        }
}