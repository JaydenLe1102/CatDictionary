package com.example.cat_dictionary_app.data

import android.os.Parcelable
import android.view.inspector.IntFlagMapping
import kotlinx.parcelize.IgnoredOnParcel
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
    val wikipedia_url: String,
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
            fun fromJsonArray(breedjsonarray: JSONArray): ArrayList<Breed>
            {
                val breeds = ArrayList<Breed>()
                for ( i in 0 until breedjsonarray.length())
                {
                    val breedJson = breedjsonarray.getJSONObject(i)
                    breeds.add(
                        Breed(
                            breedJson.getString("id"),
                            breedJson.getString("name"),
                            breedJson.getString("description"),
                            breedJson.getString("temperament"),
                            breedJson.getString("life_span"),
                            breedJson.getString("origin"),
                            breedJson.getString("wikipedia_url"),
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
                            getImageurl(breedJson.getJSONObject("image"))
                        )
                    )
                }
                return breeds
            }

            fun getImageurl(jsonObject: JSONObject):String{
                return jsonObject.getString("url")
            }

        }
}