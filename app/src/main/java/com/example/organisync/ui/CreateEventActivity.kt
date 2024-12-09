package com.example.organisync.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.organisync.databinding.ActivityCreateEventBinding
import java.util.Calendar

class CreateEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEventBinding
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.cardPhoto.setOnClickListener {
            // Handle image selection
            selectImage()
        }

        binding.btnBuat.setOnClickListener {
            createEvent()
        }
    }

    private fun selectImage() {
        // Implement image selection logic
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {
            selectedImageUri = data?.data
            binding.ivEventImage.setImageURI(selectedImageUri)
        }
    }

    private fun createEvent() {
        val title = binding.edtJudulEvent.text.toString()
        val description = binding.edtDeskripsiEvent.text.toString()

        if (title.isEmpty() || description.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
            return
        }

        val event = EventDetails(
            title = title,
            description = description,
            imageUrl = selectedImageUri.toString()
        )

        val intent = Intent(this, EventDetailActivity::class.java)
        intent.putExtra("event", event)
        startActivity(intent)
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }

    data class EventDetails(
        val title: String,
        val description: String,
        val imageUrl: String
    ) : java.io.Serializable
}