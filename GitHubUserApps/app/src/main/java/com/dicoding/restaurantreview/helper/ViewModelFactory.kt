package com.dicoding.restaurantreview.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.restaurantreview.ui.DetailViewModel
import com.dicoding.restaurantreview.ui.FavoriteViewModel
import com.dicoding.restaurantreview.ui.SwitchViewModel
import com.dicoding.restaurantreview.ui.UserViewModel

class ViewModelFactory(
    private val mApplication: Application,
    private val pref: SettingPreferences? = null
) : ViewModelProvider.Factory {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application, pref: SettingPreferences? = null): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, pref)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(pref!!) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(SwitchViewModel::class.java) -> {
                SwitchViewModel(pref!!) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

