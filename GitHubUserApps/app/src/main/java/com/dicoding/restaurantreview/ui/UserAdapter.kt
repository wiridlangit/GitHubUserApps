package com.dicoding.restaurantreview.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.restaurantreview.R
import com.dicoding.restaurantreview.data.response.User

class UserAdapter(
    private var userList: List<User>,
    private val userClickListener: UserClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    private fun onItemClick(position: Int) {
        val username = userList[position].login
        userClickListener.onUserClicked(username)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.usernameTextView.text = currentUser.login
        Glide.with(holder.avatarImageView.context)
            .load(currentUser.avatarUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.avatarImageView)
        Log.d("login", "login ${currentUser.login}")
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<User>) {
        userList = newData
        notifyDataSetChanged()
        Log.d("UserAdapter", "Data updated. New data count: ${newData.size}")
    }
}
