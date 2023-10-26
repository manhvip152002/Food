package com.example.food.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food.model.CommentModel
import com.example.food.model.FoodModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class AddFoodViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("foods")

    private val strongRef = Firebase.storage.reference
    private val imageRef = strongRef.child("foods/${UUID.randomUUID()}.jpg")


    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted: LiveData<Boolean> = _isPosted



    fun saveImage(food: String, userId: String, address: String, imageUri: Uri) {
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { imageUri ->
                saveData(food, userId, address, imageUri.toString())
            }
        }
    }



    fun saveData(food: String, userId: String, address: String, image: String) {
        val foodId = userRef.push().key!! // Lấy ID của món ăn

        val foodData = FoodModel(foodId, food, image, userId, address, System.currentTimeMillis().toString(), comments = mutableListOf())

        userRef.child(foodId).setValue(foodData)
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }.addOnFailureListener {
                _isPosted.postValue(false)
            }

    }

    // add comment for food
    fun addComment(food: FoodModel, newComment: String, userId: String, username: String) {
        val comment = CommentModel(
            commentId = UUID.randomUUID().toString(),
            userId = userId,
            username = username,
            commentText = newComment,
            timestamp = System.currentTimeMillis()
        )

        val updatedComments = food.comments.toMutableList()
        updatedComments.add(comment)
        food.comments = updatedComments

        userRef.child(food.id).child("comments").setValue(updatedComments)
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }.addOnFailureListener {
                _isPosted.postValue(false)
            }
    }



}