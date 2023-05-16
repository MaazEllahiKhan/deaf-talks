package com.example.deaftalks.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.deaftalks.R
import com.example.deaftalks.utlis.Helper
import com.example.deaftalks.utlis.SharedPref
import com.google.firebase.firestore.FirebaseFirestore

lateinit var userId:EditText
lateinit var passwordET:EditText
class LoginActivity : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    private  val TAG = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userId = findViewById(R.id.userIDET)
        passwordET = findViewById(R.id.passwordET)
    }

    fun onBackBtnClick(view: View){
        finish()
    }

    fun loginButtonClicked(view:View){
        if(isInputValid()){
            if(Helper.isInternetConnected(this)) {
                checkDataFromFireBase()
            }else{
                Toast.makeText(this,"Please Connect to WIFI !",Toast.LENGTH_LONG).show()

            }
        }else{
            Toast.makeText(this,"Fill Properly...!",Toast.LENGTH_LONG).show()
        }
    }

    private fun checkDataFromFireBase() {
        val userIdString = userId.text.trim().toString()
        val passwordString = passwordET.text.trim().toString()
        val documentRef = db.collection("users").document(userId.text?.trim().toString())
        documentRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    Log.i(TAG, "checkDataFromFireBase: "+document.data)
                    if(userIdString == document.data?.get("userId")){
                        if(passwordString == document.data?.get("password")){
                            SharedPref.getInstance(this).setIsLoggedIn(true)
                            SharedPref.getInstance(this).setUserName(userIdString)
                            val intent = Intent(this,NavDrawerActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this,"Credentials are not valid",Toast.LENGTH_LONG).show()
                        }
                    }else{

                        Toast.makeText(this,"Credentials are not valid",Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this,"Credentials are not valid",Toast.LENGTH_LONG).show()
                }
            } else {
                Log.d("TAG", "Error: ", task.exception)
            }
        }
    }

    private fun isInputValid(): Boolean {
        if(userId.text.trim().toString().isEmpty()){
            return false
        }
        if(passwordET.text.trim().toString().isEmpty()){
            return false
        }
        return true
    }
}