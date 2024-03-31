package com.dicoding.restaurantreview.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.restaurantreview.data.response.userFollow
import com.dicoding.restaurantreview.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _users = MutableLiveData<List<userFollow>>()
    val users: LiveData<List<userFollow>>
        get() = _users

    fun getFollowers(username: String) {
        ApiConfig.getApiService().getFollowers(username).enqueue(object : Callback<List<userFollow>> {
            override fun onResponse(call: Call<List<userFollow>>, response: Response<List<userFollow>>) {
                if (response.isSuccessful) {
                    _users.value = response.body()
                } else {
                    // handle
                }
            }

            override fun onFailure(call: Call<List<userFollow>>, t: Throwable) {
            }
        })
    }

    fun getFollowing(username: String) {
        ApiConfig.getApiService().getFollowing(username).enqueue(object : Callback<List<userFollow>> {
            override fun onResponse(call: Call<List<userFollow>>, response: Response<List<userFollow>>) {
                if (response.isSuccessful) {
                    _users.value = response.body()
                } else {
                    // handle
                }
            }

            override fun onFailure(call: Call<List<userFollow>>, t: Throwable) {

            }
        })
    }
}
