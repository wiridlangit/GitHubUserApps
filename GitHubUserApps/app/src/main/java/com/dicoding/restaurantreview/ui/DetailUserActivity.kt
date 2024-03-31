package com.dicoding.restaurantreview.ui

//import com.dicoding.restaurantreview.helper.UserViewModelFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.restaurantreview.R
import com.dicoding.restaurantreview.databinding.ActivityDetailUserBinding
import com.dicoding.restaurantreview.helper.ViewModelFactory
import com.dicoding.restaurantreview.repository.FavoriteUserRepository
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: SectionsPagerAdapter
    private lateinit var tabs: TabLayout
    private lateinit var favoriteUserRepository: FavoriteUserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = findViewById(R.id.progressBar)

        progressBar.visibility = View.VISIBLE

        val userViewModel = obtainViewModel(this@DetailUserActivity)


        userViewModel.detailUser.observe(this) { user ->
            if (user != null) {
                binding.usernameTextView.text = user.login
                binding.nameTextView.text = user.name
                binding.followersTextView.text = getString(R.string.followers, user.followers)
                binding.followingTextView.text = getString(R.string.following, user.following)
                progressBar.visibility = View.GONE
                Glide.with(binding.avatarImageView.context)
                    .load(user.avatarUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding.avatarImageView)
            } else {
                Log.e("DetailUserActivity", "User is null")
            }
        }

        val username = intent.getStringExtra("username")
        binding.usernameTextView.text = username

        userViewModel.fetchDetailUser(username ?: "")

        adapter = SectionsPagerAdapter(this)
        adapter.username = username ?: ""

        viewPager = findViewById(R.id.view_pager)
        tabs = findViewById(R.id.tabs)

        viewPager.adapter = adapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab1)
                1 -> getString(R.string.tab2)
                else -> "Unknown"
            }
        }.attach()

        favoriteUserRepository = FavoriteUserRepository(application)
        favoriteUserRepository.getFavoriteUserByUsername(username!!).observe(this) { user ->
            Log.d("DetailActivity", "user: $user")
            if (user != null) {
                binding.favoriteButton.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                binding.favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }

        binding.favoriteButton.setOnClickListener {
            val favUsername = userViewModel.detailUser.value?.login
            val favImg = userViewModel.detailUser.value?.avatarUrl
            if (favUsername != null) {
                userViewModel.handleFavorite(this@DetailUserActivity, favUsername, favImg)
            }

        }

    }
    override fun onDestroy() {
        super.onDestroy()
        binding
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }
}

