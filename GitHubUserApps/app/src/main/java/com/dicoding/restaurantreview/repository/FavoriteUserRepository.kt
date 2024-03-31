package com.dicoding.restaurantreview.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.restaurantreview.database.FavoriteDatabase
import com.dicoding.restaurantreview.database.FavoriteUser
import com.dicoding.restaurantreview.database.FavoriteUserDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUsers()

    fun insert(user: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insert(user) }
    }

    fun removeFromFavorites(username: String) {
        executorService.execute { mFavoriteUserDao.removeFromFavorites(username) }
    }

    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return mFavoriteUserDao.getAllFavoriteUsers()
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser?> {
        return mFavoriteUserDao.getFavoriteUserByUsername(username)
    }
}

