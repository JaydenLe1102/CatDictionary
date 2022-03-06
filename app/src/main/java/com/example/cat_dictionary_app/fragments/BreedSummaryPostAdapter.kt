package com.example.cat_dictionary_app.fragments

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cat_dictionary_app.MoreDetailsActivity
import com.example.cat_dictionary_app.R
import com.example.cat_dictionary_app.data.Breed

class BreedSummaryPostAdapter(private val context: Context, private val breeds: MutableList<Breed>)
    : RecyclerView.Adapter<BreedSummaryPostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.breed_summary_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val breed = breeds.get(position)
        holder.bind(breed)
    }

    override fun getItemCount(): Int {
        return breeds.size
    }

    // Clean all elements of the recycler
    fun clear() {
        breeds.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(tweetList: List<Breed>) {
        breeds.addAll(tweetList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val tvBreedName = itemView.findViewById<TextView>(R.id.tvBreedName)
        val ivBreedImage = itemView.findViewById<ImageView>(R.id.ivBreedImage)
        val tvBreedDescription = itemView.findViewById<TextView>(R.id.tvBreedDescription)

        // register the ClickListener
        // Every time we create a new ViewHolder,
        init {
            // "this" refers to the class ViewHodler, and the class is implementing an appropriate
            // interface View.OnClickListener. That's why "this" works.
            itemView.setOnClickListener(this)
        }

        fun bind(breed: Breed) {
            tvBreedName.text = breed.name;
            tvBreedDescription.text = breed.description;
            val desLen = breed.description.length
            val des = breed.description
            Log.i(TAG, "description has len $desLen: $des")

            // use Glide to bind image to its ivBreedImage
            Glide.with(itemView.context).load(breed.imageurl).into(ivBreedImage)
        }

        // Start MoreDetails Activity when View is clicked
        override fun onClick(p0: View?) {
            val breed = breeds[adapterPosition]
            val intent = Intent(context, MoreDetailsActivity::class.java)
            intent.putExtra("BREED_EXTRA", breed)
            context.startActivity(intent)
        }

    }

    companion object {
        val TAG = "BreedSummaryPostAdapter"
    }
}
