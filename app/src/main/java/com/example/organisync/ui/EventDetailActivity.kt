package com.example.organisync.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.organisync.databinding.ActivityEventDetailBinding
import com.example.organisync.model.News

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        setupViews()
        setupBackButton()
    }

    private fun setupViews() {
        // Assuming you have an Event object with the details
        val event = getEventDetails()

        binding.apply {
            tvEventTitle.text = event.title
            tvEventDescription.text = event.deskripsi
            Glide.with(this@EventDetailActivity)
                .load(event.photo)
                .into(ivEventImage)
        }
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
    private fun getEventDetails(): News {
        // Replace this with actual event details retrieval logic
        return News(
            id = 0,
            title = intent.getStringExtra("title").toString(),
            deskripsi = intent.getStringExtra("description").toString(),
            photo = intent.getStringExtra("photo").toString(),
            asalUniversitas = intent.getStringExtra("universitas").toString(),
            namaOrganisasi = intent.getStringExtra("organisasi").toString()
        )
    }

    data class EventDetails(
        val title: String,
        val description: String,
        val imageUrl: String
    )
}