package com.example.food.item_view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.food.model.CommentModel

@Composable
fun BasicTextFieldWithHint(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Box() {
        if (value.isEmpty()) {
            Text(text = hint, color = Color.Gray)
        }
        BasicTextField(value = value, onValueChange = onValueChange, textStyle = TextStyle.Default.copy(color = Color.Black))
    }
}