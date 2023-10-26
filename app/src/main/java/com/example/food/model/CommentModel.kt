package com.example.food.model

data class CommentModel(
    var commentId: String = "",
    var userId: String = "",
    var username: String = "",
    var commentText: String = "",
    var timestamp: Long = 0
)
