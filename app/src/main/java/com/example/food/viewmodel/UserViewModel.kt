package com.example.food.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food.model.FoodModel
import com.example.food.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    val foodRef = db.getReference("foods")
    val userRef = db.getReference("users")

    private val _foods = MutableLiveData(listOf<FoodModel>())

    val foods : LiveData<List<FoodModel>> get() = _foods

    private val _followerList = MutableLiveData(listOf<String>())

    val followerList : LiveData<List<String>> get() = _followerList

    private val _followingList = MutableLiveData(listOf<String>())

    val followingList : LiveData<List<String>> get() = _followingList

    private val _users = MutableLiveData(UserModel())

    val users : LiveData<UserModel> get() = _users

    fun fetchUser(uid:String){

        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue((UserModel:: class.java))
                _users.postValue(user)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchFoods(uid:String){

        foodRef.orderByChild("userId").equalTo(uid).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val foodList = snapshot.children.mapNotNull {
                    it.getValue(FoodModel::class.java)
                }
                _foods.postValue(foodList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    val firestoreDb = Firebase.firestore

    fun followUsers(userId:String, currentUserId:String) {
        val ref = firestoreDb.collection("following").document(currentUserId)
        val followerRef = firestoreDb.collection("followes").document(userId)

        ref.update("followingIds", FieldValue.arrayUnion(userId))
        followerRef.update("followerIds", FieldValue.arrayUnion(currentUserId))
    }

    fun getFollowers(userId: String) {

        firestoreDb.collection("followers").document(userId)
            .addSnapshotListener { value, error ->

                val followerIds = value?.get("followerIds") as? List<String> ?: listOf()
                _followerList.postValue(followerIds)
            }
    }

    fun getFollowing(userId: String) {
        Log.d("getFollowing", userId);

        firestoreDb.collection("following").document(userId)
            .addSnapshotListener { value, error ->

                val followerIds = value?.get("followingIds") as? List<String> ?: listOf()
                _followingList.postValue(followerIds)
            }

    }

}