package com.dicoding.restaurantreview.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.restaurantreview.R
import com.dicoding.restaurantreview.helper.SettingPreferences
import com.dicoding.restaurantreview.helper.ViewModelFactory
import com.dicoding.restaurantreview.helper.dataStore
import com.google.android.material.switchmaterial.SwitchMaterial

class ThemeSwitchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)


        val switchViewModel = obtainViewModel(this@ThemeSwitchActivity)
        switchViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            switchViewModel.saveThemeSetting(isChecked)
        }
    }
    private fun obtainViewModel(activity: AppCompatActivity): SwitchViewModel {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val factory = ViewModelFactory.getInstance(application, pref)
        return ViewModelProvider(activity, factory)[SwitchViewModel::class.java]
    }
}
