package com.example.organisync.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.organisync.MainActivity
import com.example.organisync.R
import com.example.organisync.User.UserPreferences
import com.example.organisync.ViewModel.ViewModelFactory
import com.example.organisync.ViewModel.ViewModelLogin
import com.example.organisync.databinding.ActivityLoginBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class activity_login : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: ViewModelLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        loginViewModel = ViewModelProvider(this, ViewModelFactory(UserPreferences.getInstance(dataStore)))[ViewModelLogin::class.java]

        initializeUI()
    }

    private fun initializeUI() {
        playAnimation()
        setUpAction()
        observeLogin()
    }

    private fun setUpAction() {
        binding.signInButton.setOnClickListener {
            val email = binding.emailInput.text
            val password = binding.passwordInput.text
            when {
                email.isNullOrEmpty() -> {
                    Toast.makeText(this, "Harap isi email anda", Toast.LENGTH_SHORT).show()
                }
                password.isNullOrEmpty() -> {
                    Toast.makeText(this, "Harap isi password anda", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    setUpLogin()
                    showLoading(true)
                }
            }
        }
        binding.signUpText.setOnClickListener {
            val intent = Intent(this@activity_login, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun setUpLogin() {
        binding.apply {
            val email = emailInput.text.toString()
            val pass = passwordInput.text.toString()
            loginViewModel.login(email, pass)
        }
    }

    private fun observeLogin() {
        loginViewModel.getLogin().observe(this) {
            if (it.error) {
                showLoading(false)
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            } else {
                showLoading(true)
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@activity_login, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val loginImage = ObjectAnimator.ofFloat(binding.imageLogin, View.ALPHA, 1f).setDuration(500)
        val txtLogin = ObjectAnimator.ofFloat(binding.titleText, View.ALPHA, 1f).setDuration(500)
        val emailEditText = ObjectAnimator.ofFloat(binding.emailInput, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordInput, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.signInButton, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.signUpText, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playSequentially(loginImage, txtLogin, emailEditText, passwordEditTextLayout, login, register)
        }
        together.start()
    }

    private fun showLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }
}