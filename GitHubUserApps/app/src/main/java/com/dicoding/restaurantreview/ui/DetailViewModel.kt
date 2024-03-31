package com.dicoding.restaurantreview.ui

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.restaurantreview.data.response.DetailUserResponse
import com.dicoding.restaurantreview.data.retrofit.ApiConfig
import com.dicoding.restaurantreview.database.FavoriteUser
import com.dicoding.restaurantreview.repository.FavoriteUserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel(private val application: Application) : ViewModel() {
    private var favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailUser: LiveData<DetailUserResponse?>
        get() = _detailUser


    private val _existingFavorite = MutableLiveData<FavoriteUser?>()
    val existingFavorite: LiveData<FavoriteUser?> = _existingFavorite

    fun fetchDetailUser(username: String) {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getDetailUser(username)
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    // Handle
                }
            } catch (e: HttpException) {
                // Handle
            }
        }
    }

    fun insert(user: FavoriteUser) {
        favoriteUserRepository.insert(user)
    }

    fun handleFavorite(owner: LifecycleOwner, username: String, avatarUrl: String?) {
        _existingFavorite.postValue(null)
        var actionCompleted = false

        favoriteUserRepository.getFavoriteUserByUsername(username).observe(owner) { user ->
            if (!actionCompleted) {
                if (user == null) {
                    val newUser = FavoriteUser().apply {
                        this.username = username
                        this.avatarUrl = avatarUrl
                    }
                    insert(newUser)
                } else {
                    favoriteUserRepository.removeFromFavorites(username)
                }
                actionCompleted = true
            }
        }
    }

}