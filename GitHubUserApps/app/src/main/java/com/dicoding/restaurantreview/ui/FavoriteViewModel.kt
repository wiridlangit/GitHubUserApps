package com.dicoding.restaurantreview.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.restaurantreview.database.FavoriteUser
import com.dicoding.restaurantreview.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = favoriteUserRepository.getAllFavoriteUsers()
}
