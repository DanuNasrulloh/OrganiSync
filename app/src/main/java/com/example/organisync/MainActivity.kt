package com.example.organisync

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organisync.User.UserPreferences
import com.example.organisync.ViewModel.ViewModelFactory
import com.example.organisync.ViewModel.ViewModelMain
import com.example.organisync.adapter.Adapter
import com.example.organisync.databinding.ActivityMainBinding
import com.example.organisync.model.News
import com.example.organisync.ui.Event
import com.example.organisync.ui.NewsDetailActivity
import com.example.organisync.ui.activity_login
import com.example.organisync.ui.newsactivity

class MainActivity : AppCompatActivity() {

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: ViewModelMain
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[ViewModelMain::class.java]

        mainViewModel.getUser().observe(this) { user ->
            if (user.token.isNotEmpty()) {
                mainViewModel.setStory(user.token)
                Log.d("result main :", user.token)
            } else {
                startActivity(Intent(this, activity_login::class.java))
                finish()
            }
        }

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
                Intent(this@MainActivity, NewsDetailActivity::class.java).also {
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
            newsRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            newsRecyclerView.setHasFixedSize(true)
            newsRecyclerView.adapter = adapter
        }
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    // Navigate to NewsActivity
                    val intent = Intent(this, newsactivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.Event -> {
                    // Navigate to EventActivity
                    val intent = Intent(this, Event::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        binding.Imagelogout.setOnClickListener {
            // Log out and navigate to login activity
            val intent = Intent(this, activity_login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}