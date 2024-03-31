package com.dicoding.restaurantreview.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.restaurantreview.database.FavoriteUser
import com.dicoding.restaurantreview.R
import com.dicoding.restaurantreview.helper.FavoriteDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val listFavoriteUsers = ArrayList<FavoriteUser>()


    fun setListNotes(listNotes: List<FavoriteUser>) {
        val diffCallback = FavoriteDiffCallback(this.listFavoriteUsers, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteUsers.clear()
        this.listFavoriteUsers.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentUser = listFavoriteUsers[position]
        holder.bind(currentUser)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra("username", currentUser.username)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listFavoriteUsers.size
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        fun bind(favoriteUser: FavoriteUser) {
            usernameTextView.text = favoriteUser.username

            Glide.with(itemView.context)
                .load(favoriteUser.avatarUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(avatarImageView)
        }
    }
}
