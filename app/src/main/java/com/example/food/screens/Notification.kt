package com.example.food.screens

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.food.item_view.FoodItem
import com.example.food.viewmodel.HomeViewModel
import com.example.food.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Notification(navHostController: NavHostController) {
    val userViewModel: UserViewModel = viewModel()

    val followerList by userViewModel.followerList.observeAsState(null)
    val followingList by userViewModel.followingList.observeAsState(null)

    if (FirebaseAuth.getInstance().currentUser!!.uid!= "") {
        userViewModel.getFollowers(FirebaseAuth.getInstance().currentUser!!.uid)
        userViewModel.getFollowing(FirebaseAuth.getInstance().currentUser!!.uid)
    }

    Log.d("getFollowing", followingList!!.size.toString());

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp) // Padding để tránh cạnh top và left
    ) {
        items(followingList ?: emptyList()) { notification ->
            Text(text = "Bạn đang follow một người mới")
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
        }
    }
}