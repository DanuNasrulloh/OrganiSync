package com.example.organisync.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organisync.MainActivity
import com.example.organisync.R
import com.example.organisync.adapter.Adapter
import com.example.organisync.databinding.ActivityNewsactivityBinding
import com.example.organisync.model.News
import com.google.android.material.bottomnavigation.BottomNavigationView

class newsactivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsactivityBinding
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        adapter = Adapter()
        val datadummy = ArrayList<News>()
        datadummy.addAll(
            arrayListOf(
                News(
                    0,
                    "News 1",
                    "https://news.uad.ac.id/wp-content/uploads/Kampus-4-Universitas-Ahmad-Dahlan.jpg",
                    "Himpunan uad",
                    "Universitas Ahmad Dahlan",
                    "uad unggul"
                ),
                News(
                    1,
                    "News 2",
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
                Intent(this@newsactivity, NewsDetailActivity::class.java).also {
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
            newsRecyclerView.layoutManager = LinearLayoutManager(this@newsactivity)
            newsRecyclerView.setHasFixedSize(true)
            newsRecyclerView.adapter = adapter
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Event -> {
                    // Navigate to EventActivity
                    val intent = Intent(this, Event::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_home -> {
                    // Navigate to MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}