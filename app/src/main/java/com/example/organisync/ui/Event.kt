package com.example.organisync.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organisync.MainActivity
import com.example.organisync.R
import com.example.organisync.ViewModel.ViewModelFactory
import com.example.organisync.ViewModel.ViewModelMain
import com.example.organisync.adapter.Adapter
import com.example.organisync.databinding.ActivityEventBinding
import com.example.organisync.model.EventAcara
import com.example.organisync.model.News
import com.example.organisync.ui.activity_login
import com.google.android.material.bottomnavigation.BottomNavigationView

class Event : AppCompatActivity() {

    private lateinit var binding: ActivityEventBinding
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        adapter = Adapter()
        val datadummy = ArrayList<News>()
        datadummy.addAll(
            arrayListOf(
                News(
                    0,
                    "Event 1",
                    "https://news.uad.ac.id/wp-content/uploads/Kampus-4-Universitas-Ahmad-Dahlan.jpg",
                    "Himpunan uad",
                    "Universitas Ahmad Dahlan",
                    "uad unggul"
                ),
                News(
                    1,
                    "Event 2",
                    "https://news.uad.ac.id/wp-content/uploads/Kampus-4-Universitas-Ahmad-Dahlan.jpg",
                    "Himpunan uad",
                    "Universitas Ahmad Dahlan",
                    "uad unggul"
                ),
            )
        )
        adapter.setList(datadummy)
        adapter.notifyDataSetChanged()
        adapter.setOnClickCallback(object : Adapter.OnItemClickCallback {
            override fun onItemClicked(data: News) {
                Intent(this@Event, EventDetailActivity::class.java).also {
                    it.putExtra("title", data.title)
                    it.putExtra("description", data.deskripsi)
                    it.putExtra("photo", data.photo)
                    it.putExtra("organisasi", data.namaOrganisasi)
                    it.putExtra("universitas", data.asalUniversitas)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            EventRecyclerView.layoutManager = LinearLayoutManager(this@Event)
            EventRecyclerView.setHasFixedSize(true)
            EventRecyclerView.adapter = adapter
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_dashboard -> {
                    // Navigate to NewsActivity
                    val intent = Intent(this, newsactivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}