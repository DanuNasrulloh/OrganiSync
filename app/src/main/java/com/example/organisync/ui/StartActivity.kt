package com.example.organisync.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.organisync.R
import com.example.organisync.databinding.ActivityStartBinding
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        val startButton: Button = findViewById(R.id.button_Start)
        startButton.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
        }
        initializeUI()
    }
    private fun initializeUI() {
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageCowok, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        ObjectAnimator.ofFloat(binding.imageCewek, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        ObjectAnimator.ofFloat(binding.imagepesan, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val startImagecowok = ObjectAnimator.ofFloat(binding.imageCowok, View.ALPHA, 1f).setDuration(500)
        val startImagecewek = ObjectAnimator.ofFloat(binding.imageCewek, View.ALPHA, 1f).setDuration(500)
        val startImagepesan = ObjectAnimator.ofFloat(binding.imagepesan, View.ALPHA, 1f).setDuration(500)
        val StartLogin = ObjectAnimator.ofFloat(binding.buttonStart, View.ALPHA, 1f).setDuration(500)
        val StartCaption = ObjectAnimator.ofFloat(binding.captionStart, View.ALPHA, 1f).setDuration(500)
        val StartTitle = ObjectAnimator.ofFloat(binding.OrganiSync, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(StartLogin,startImagepesan , startImagecewek, startImagecowok, StartCaption, StartTitle)
        }.start()
    }
}