package com.example.food.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food.model.FoodModel
import com.example.food.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    val food = db.getReference("foods")



    private val _foodsAndUsers = MutableLiveData<List<Pair<FoodModel, UserModel>>>()
    val foodsAndUsers: LiveData<List<Pair<FoodModel, UserModel>>> = _foodsAndUsers

    init {
        fetchFoodsAndUsers {
            _foodsAndUsers.value = it
        }
    }

    private fun fetchFoodsAndUsers(onResult: (List<Pair<FoodModel, UserModel>>)-> Unit){
        food.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<Pair<FoodModel, UserModel>>()
                for (foodSnapshot in snapshot.children){
                    val food = foodSnapshot.getValue(FoodModel::class.java)
                    food.let {
                        fetchUserFromFood(it!!){
                                user ->
                            result.add(0,it to user)

                            if (result.size == snapshot.childrenCount.toInt()){
                                onResult(result)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun fetchUserFromFood(food: FoodModel, onResult: (UserModel)-> Unit) {
        db.getReference("users").child(food.userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    user?.let(onResult)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

}