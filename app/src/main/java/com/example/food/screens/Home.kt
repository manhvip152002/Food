package com.example.food.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.food.item_view.FoodItem
import com.example.food.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home(navHostController: NavHostController) {
    val homeViewModel: HomeViewModel = viewModel()
    val foodAndUsers by homeViewModel.foodsAndUsers.observeAsState(null)

    LazyColumn {
        items(foodAndUsers ?: emptyList()) { pairs ->
            FoodItem(
                food = pairs.first,
                users = pairs.second,
                navHostController,
                FirebaseAuth.getInstance().currentUser!!.uid,
            )
        }
    }
}
