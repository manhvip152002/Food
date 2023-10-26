package com.example.food.item_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.food.R
import com.example.food.model.CommentModel
import com.example.food.model.FoodModel
import com.example.food.model.UserModel
import com.example.food.navigation.Routes
import com.example.food.viewmodel.AddFoodViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.*

@Composable
fun FoodItem(
    food: FoodModel,
    users: UserModel,
    navHostController: NavHostController,
    userId: String
) {
    val foodViewModel: AddFoodViewModel = viewModel()

    var newComment by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User Avatar
                Image(
                    painter = rememberAsyncImagePainter(model = users.imageUrl),
                    contentDescription = null, // Add meaningful description
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                // User Info
                Column {
                    Text(
                        text = users.name,
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    )

                    Text(
                        text = food.food,
                        style = TextStyle(fontSize = 16.sp)
                    )

                    Text(
                        text = food.address,
                        style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                    )
                }
            }

            // Food Image
            if (food.image.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = food.image),
                    contentDescription = null, // Add meaningful description
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Add Comment Section
            BasicTextFieldWithHint(
                hint = "Add a comment",
                value = newComment,
                onValueChange = { newComment = it }
            )
            TextButton(
                onClick = {
                    foodViewModel.addComment(
                        food,
                        newComment,
                        FirebaseAuth.getInstance().currentUser!!.uid,
                        users.name
                    )
                    newComment = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(text = "Post Comment", style = TextStyle(fontSize = 16.sp))
            }

            // google map
            TextButton(
                onClick = {
                    navHostController.navigate(Routes.Maps.routes)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(text = "Go to Maps", style = TextStyle(fontSize = 16.sp))
            }


            // Comments
            food.comments.forEach { comment ->
                CommentItem(comment = comment)
            }

            // Divider
            Divider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}

