package com.dicoding.restaurantreview.ui


import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.restaurantreview.data.response.DetailUserResponse
import com.dicoding.restaurantreview.data.response.User
import com.dicoding.restaurantreview.data.retrofit.ApiConfig
import com.dicoding.restaurantreview.database.FavoriteUser
import com.dicoding.restaurantreview.helper.SettingPreferences
import com.dicoding.restaurantreview.repository.FavoriteUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserViewModel(private val pref: SettingPreferences) : ViewModel(), CoroutineScope {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    override val coroutineContext = viewModelScope.coroutineContext

    fun fetchUserList(query: String) {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().searchUsers(query)
                _users.value = response.body()?.items
            } catch (e: HttpException) {
                // Handle network errors
            }
        }
    }


    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}
