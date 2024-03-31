package com.dicoding.restaurantreview.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.restaurantreview.databinding.ActivityFavoriteBinding
import com.dicoding.restaurantreview.helper.ViewModelFactory


class FavoriteActivity : AppCompatActivity() {
    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavoriteUsers().observe(this) { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        }

        adapter = FavoriteAdapter()
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this)
        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.adapter = adapter

    }
    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

}
