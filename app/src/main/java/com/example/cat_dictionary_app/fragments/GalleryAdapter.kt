package com.example.cat_dictionary_app.fragments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cat_dictionary_app.R
import com.example.cat_dictionary_app.data.Breed
import com.example.cat_dictionary_app.data.GalleryImage

class GalleryAdapter(private val context: Context, private val galleryImages: MutableList<GalleryImage>)
    : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.gallery_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val galleryImage = galleryImages[position]
        holder.bind(galleryImage)
    }

    override fun getItemCount(): Int {
        return galleryImages.size
    }

    // Clean all elements of the recycler
    fun clear() {
        galleryImages.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(galleryImageList: List<GalleryImage>) {
        galleryImages.addAll(galleryImageList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivGalleryImage = itemView.findViewById<ImageView>(R.id.ivGalleryImage)

        fun bind(galleryImage: GalleryImage) {
            // use Glide to bind image to its ivGalleryImage
            Glide.with(itemView.context).load(galleryImage.url).into(ivGalleryImage)
        }

    }

    companion object {
        val TAG = "GalleryAdapterAdapter"
    }
}
