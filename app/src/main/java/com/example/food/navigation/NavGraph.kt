package com.example.food.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.food.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
//chạy vào logo trước
    NavHost(navController = navController,
        startDestination = Routes.Splash.routes) {

        composable(Routes.Splash.routes){
            Splash(navController)
        }

        composable(Routes.Home.routes){
            Home(navController)
        }

        composable(Routes.Maps.routes){
            Maps(navController)
        }

        composable(Routes.Notification.routes){
            Notification(navController)
        }

        composable(Routes.Search.routes){
            Search(navController)
        }

        composable(Routes.Add.routes){
            Add(navController)
        }

        composable(Routes.Profile.routes){
            Profile(navController)
        }

        composable(Routes.BottomNav.routes){
            BottomNav(navController)
        }

        composable(Routes.Login.routes){
            Login(navController)
        }

        composable(Routes.Register.routes){
            Register(navController)
        }

        composable(Routes.OtherUser.routes){
            val data = it.arguments!!.getString("data")
            OtherUser(navController, data!!)
        }
    }
}