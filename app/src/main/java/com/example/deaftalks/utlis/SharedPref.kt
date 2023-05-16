package com.example.deaftalks.utlis

import android.content.Context
import android.content.SharedPreferences

class SharedPref private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

    private final val _isLoggedInKey = "isLoggedInKey"
    private final val _userNameKey = "userNameKey"

    fun saveString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun setIsLoggedIn(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(_isLoggedInKey, value)
        editor.apply()
    }

    fun getIsLoggedIn():Boolean {
        return sharedPreferences.getBoolean(_isLoggedInKey,false)
    }

    fun setUserName(value:String) {
        val editor = sharedPreferences.edit()
        editor.putString(_userNameKey,value)
        editor.apply()
    }

    fun getUserName():String {
        return sharedPreferences.getString(_userNameKey, "") ?: "Test"
    }

    // Add more methods for saving/retrieving different data types as needed

    companion object {
        @Volatile
        private var instance: SharedPref? = null

        fun getInstance(context: Context): SharedPref {
            return instance ?: synchronized(this) {
                instance ?: SharedPref(context).also { instance = it }
            }
        }
    }
}
