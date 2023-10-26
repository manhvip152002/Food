package com.example.food.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.food.item_view.FoodItem
import com.example.food.model.UserModel
import com.example.food.navigation.Routes
import com.example.food.utils.SharedPref
import com.example.food.viewmodel.AuthViewModel
import com.example.food.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun OtherUser(navHostController:NavHostController, uid: String) {

    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val userViewModel: UserViewModel = viewModel()
    val foods by userViewModel.foods.observeAsState(null)
    val users by userViewModel.users.observeAsState(null)
    val followerList by userViewModel.followerList.observeAsState(null)
    val followingList by userViewModel.followingList.observeAsState(null)

    userViewModel.fetchFoods(uid)
    userViewModel.fetchUser(uid)
    userViewModel.getFollowers(uid)
    userViewModel.getFollowing(uid)

    var currentUserId = ""

    if (FirebaseAuth.getInstance().currentUser != null)
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid


    LaunchedEffect(firebaseUser) {
        if (firebaseUser == null) {
            navHostController.navigate(Routes.Login.routes) {
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    LazyColumn() {
        item {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val (text, logo, userName, button,
                    bio, followers, following) = createRefs()


                Text(text = users!!.name, style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                ), modifier = Modifier.constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                )

                Image(painter = rememberAsyncImagePainter(model = users!!.imageUrl),
                    contentDescription = "Close",
                    modifier = Modifier
                        .constrainAs(logo) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(text = users!!.userName, style = TextStyle(
                    fontSize = 20.sp
                ), modifier = Modifier.constrainAs(userName) {
                    top.linkTo(text.bottom)
                    start.linkTo(parent.start)
                }
                )

                Text(text = users!!.bio, style = TextStyle(
                    fontSize = 20.sp
                ), modifier = Modifier.constrainAs(bio) {
                    top.linkTo(userName.bottom)
                    start.linkTo(parent.start)
                }
                )

                Text(text =
                "${followerList?.size} Followers", style = TextStyle(
                    fontSize = 20.sp
                ), modifier = Modifier.constrainAs(followers) {
                    top.linkTo(bio.bottom)
                    start.linkTo(parent.start)
                }
                )

                Text(text =
                "${followingList?.size} Following", style = TextStyle(
                    fontSize = 20.sp
                ), modifier = Modifier.constrainAs(following) {
                    top.linkTo(followers.bottom)
                    start.linkTo(parent.start)
                }
                )
                ElevatedButton(onClick = {
                    if ( currentUserId != null)
                        userViewModel.followUsers(uid, currentUserId) },
                    modifier = Modifier.constrainAs(button) {
                        top.linkTo(following.bottom)
                        start.linkTo(parent.start)
                    }) {
                    Text(text = if (followerList != null && followerList!!.isEmpty() && followerList!!.contains(currentUserId)) "Following" else "Follow")

                }
            }

        }
        if (foods != null && users != null){
            items(foods ?: emptyList()) { pair ->
                FoodItem(
                    food = pair,
                    users = users!!,
                    navHostController = navHostController,
                    userId = SharedPref.getUserName(context)
                )
            }
        }
    }

}