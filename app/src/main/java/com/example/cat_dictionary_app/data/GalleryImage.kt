package com.example.cat_dictionary_app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

@Parcelize
data class GalleryImage (
    val url: String

):Parcelable {

    companion object{
        val TAG = "ImageDataModel"

        fun fromJsonArray(imagejsonarray: JSONArray): ArrayList<GalleryImage>
        {
            val images = ArrayList<GalleryImage>()
            for ( i in 0 until imagejsonarray.length())
            {
                val imageJson = imagejsonarray.getJSONObject(i)


                images.add(
                    GalleryImage(
                        imageJson.getString("url")
                    )
                )
            }
            return images
        }

    }
}