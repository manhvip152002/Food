package com.example.food.utils

import android.content.Context

object SharedPref {

    fun storeData(
        name: String,
        email: String,
        bio: String,
        userName: String,
        imageUrl: String,
        context: Context
    ) {
        val sharedPreferences = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("email", email)
        editor.putString("bio", bio)
        editor.putString("userName", userName)
        editor.putString("imageUrl", imageUrl)
        editor.apply()

    }
    fun getUserName(context: Context): String{
        val sharedPreferences = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userName","")!!
    }

    fun getName(context: Context): String{
        val sharedPreferences = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPreferences.getString("name","")!!
    }
    fun getEmail(context: Context): String{
        val sharedPreferences = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email","")!!
    }
    fun getBio(context: Context): String{
        val sharedPreferences = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPreferences.getString("bio","")!!
    }

    fun getImage(context: Context): String{
        val sharedPreferences = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPreferences.getString("imageUrl","")!!
    }
}