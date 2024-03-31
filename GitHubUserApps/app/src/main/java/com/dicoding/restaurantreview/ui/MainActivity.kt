package com.dicoding.restaurantreview.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.restaurantreview.R
import com.dicoding.restaurantreview.helper.SettingPreferences
import com.dicoding.restaurantreview.helper.ViewModelFactory
import com.dicoding.restaurantreview.helper.dataStore


class MainActivity : AppCompatActivity(), UserClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(emptyList(), this)
        recyclerView.adapter = adapter
        progressBar.visibility = View.VISIBLE


        val viewModel = obtainViewModel(this@MainActivity)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        viewModel.fetchUserList("q")

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (query.isNullOrEmpty()) {
                    Log.d("MainActivity", "Creating toast")
                    Toast.makeText(this@MainActivity, "Please enter a username", Toast.LENGTH_SHORT).show()
                    false
                } else {
                    progressBar.visibility = View.VISIBLE
                    viewModel.fetchUserList(query)
                    true
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        searchView.setOnClickListener {
            searchView.isIconified = false
        }

        viewModel.users.observe(this) { users ->
            users?.let {
                adapter.updateData(users)
                progressBar.visibility = View.GONE
            } ?: run {
                progressBar.visibility = View.GONE
            }
        }

        viewModel.getThemeSettings().observe(this) {isDarkModeActive : Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favoriteButton -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.settingsButton -> {
                val intent = Intent(this, ThemeSwitchActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onUserClicked(username: String) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }
    private fun obtainViewModel(activity: AppCompatActivity): UserViewModel {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val factory = ViewModelFactory.getInstance(application, pref)
        return ViewModelProvider(activity, factory)[UserViewModel::class.java]
    }

}
