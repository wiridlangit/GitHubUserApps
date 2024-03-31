package com.dicoding.restaurantreview.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.restaurantreview.R
import com.bumptech.glide.Glide
import com.dicoding.restaurantreview.data.response.userFollow

class FollowAdapter(private val users: List<userFollow>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val usernameTextView: TextView = view.findViewById(R.id.usernameTextView)
        val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.usernameTextView.text = user.login
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.avatarImageView)
    }



    override fun getItemCount(): Int {
        return users.size
    }
}
