package com.example.food.navigation

sealed class Routes (val routes: String) {

    object Home: Routes("home")
    object Notification: Routes("notification")
    object Profile: Routes("profile")
    object Search: Routes("search")
    object Add: Routes("add")
    object Splash: Routes("splash")
    object BottomNav: Routes("bottom_nav")
    object Login: Routes("login")
    object Register: Routes("register")
    object OtherUser: Routes("other_users/{data}")
    object Maps: Routes("maps")

}
