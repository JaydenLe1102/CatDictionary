package com.example.cat_dictionary_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cat_dictionary_app.data.Breed

class MoreDetailsActivity : AppCompatActivity() {

    private lateinit var tvBreedName: TextView
    private lateinit var tvBreedDescription: TextView
    private lateinit var tvLifeSpan: TextView
    private lateinit var tvOrigin: TextView
    private lateinit var tvTemperament: TextView
    private lateinit var ivBreedImage: ImageView
    private lateinit var rbAffectionLevel: RatingBar
    private lateinit var rbEnergyLevel: RatingBar
    private lateinit var rbIntelligence: RatingBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_details)
        tvBreedName = findViewById(R.id.tvBreedName)
        tvBreedDescription = findViewById(R.id.tvBreedDescription)
        tvLifeSpan = findViewById(R.id.tvLifeSpan)
        tvOrigin = findViewById(R.id.tvOrigin)
        tvTemperament = findViewById(R.id.tvTemperament)
        ivBreedImage = findViewById(R.id.ivBreedImage)
        rbAffectionLevel = findViewById(R.id.rbAffectionLevel)
        rbEnergyLevel = findViewById(R.id.rbEnergyLevel)
        rbIntelligence = findViewById(R.id.rbIntelligence)

        findViewById<Button>(R.id.btBack).setOnClickListener {
            finish()
        }

        val breed = intent.getParcelableExtra<Breed>("BREED_EXTRA") as Breed
        tvBreedName.text = breed.name
        tvBreedDescription.text = breed.description
        var lifeSpanText = "Life Span: " + breed.life_span + " Years"
        tvLifeSpan.text = lifeSpanText
        var originText = "Origin: " + breed.origin
        tvOrigin.text = originText
        tvTemperament.text = breed.temperament
        rbAffectionLevel.rating = breed.affection_level.toFloat()
        rbEnergyLevel.rating = breed.energy_level.toFloat()
        rbIntelligence.rating = breed.intelligence.toFloat()

        Glide.with(this).load(breed.imageurl).into(ivBreedImage)
    }
}