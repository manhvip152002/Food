package com.example.food.model

data class FoodModel(
    val id: String = "",
    val food: String = "",
    val image: String = "",
    val userId: String = "",
    val address: String = "",
    val timeStamp: String = "",
    var comments: List<CommentModel> = emptyList()
) {
    // Constructor không đối số
    constructor() : this("","", "", "", "", "", emptyList())
}
