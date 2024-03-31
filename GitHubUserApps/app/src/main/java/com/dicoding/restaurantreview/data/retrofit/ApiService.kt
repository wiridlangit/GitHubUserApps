package com.dicoding.restaurantreview.data.retrofit

import retrofit2.Call
import com.dicoding.restaurantreview.data.response.DetailUserResponse
import com.dicoding.restaurantreview.data.response.GitHubResponse
import com.dicoding.restaurantreview.data.response.userFollow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") username: String): Response<GitHubResponse>

    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String): Response<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<userFollow>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<userFollow>>
}