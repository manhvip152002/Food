package com.example.food.item_view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.food.model.CommentModel
import java.time.format.TextStyle

@Composable
fun CommentItem(comment: CommentModel) {
    Column {
        Text(
            text = comment.username,
            style = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold)
        )
        Text(text = comment.commentText)
    }
}