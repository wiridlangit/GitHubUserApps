package com.dicoding.restaurantreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.restaurantreview.ui.FollowAdapter
import com.dicoding.restaurantreview.ui.FollowViewModel

class FollowFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingBar: ProgressBar
    private lateinit var viewModel: FollowViewModel
    private var position: Int = 0
    private var username: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_follow, container, false)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
        }

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        loadingBar = view.findViewById(R.id.loadingBar)

        viewModel = ViewModelProvider(this)[FollowViewModel::class.java]

        when (position) {
            1 -> viewModel.getFollowers(username)
            2 -> viewModel.getFollowing(username)
        }

        viewModel.users.observe(viewLifecycleOwner) { users ->
            recyclerView.adapter = FollowAdapter(users)
            loadingBar.visibility = View.GONE
        }

        return view
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}
