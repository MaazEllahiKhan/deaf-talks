package com.example.deaftalks.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.deaftalks.R
import com.example.deaftalks.utlis.SharedPref

class LandingActivity : AppCompatActivity() {
    private  val TAG= "LandingActivity"

    lateinit var loginBtn:TextView
    lateinit var signUpBtn:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        //logi
        if(SharedPref.getInstance(this).getIsLoggedIn()){
            val intent = Intent(this, NavDrawerActivity::class.java)
            startActivity(intent)
            finish()
        }
        loginBtn = findViewById(R.id.loginBtn)
        signUpBtn = findViewById(R.id.signupBtn)
        loginBtn.setOnClickListener { loginBtn() }
        signUpBtn.setOnClickListener{signUpBtn()}

    }

    fun signUpBtn() {
        Log.i(TAG, "signUpBtn: clicked")
        val intent = Intent(this, RegisterActivity::class.java)
           startActivity(intent)
    }

    fun loginBtn() {
        Log.i(TAG, "loginBtn: clicked")
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

}