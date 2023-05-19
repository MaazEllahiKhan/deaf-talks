package com.example.deaftalks.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deaftalks.R
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Pattern
import androidx.lifecycle.lifecycleScope
import com.example.deaftalks.utlis.Helper
import com.example.deaftalks.utlis.SharedPref
import kotlinx.coroutines.*


class RegisterActivity : AppCompatActivity() {

    var db = FirebaseFirestore.getInstance()

    lateinit var firstNameET: EditText

    lateinit var userIdET: EditText

    lateinit var passwordET: EditText

    lateinit var progressBar: ProgressBar

    lateinit var createAccBtn: Button

    private final val TAG = "RegisterActivity"

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        firstNameET = findViewById(R.id.userNameET)
        userIdET = findViewById(R.id.userID)
        passwordET = findViewById(R.id.userPasswordET)
        progressBar = findViewById(R.id.progressBarRScreen)
        createAccBtn = findViewById(R.id.registerButton)
        setProgressBarInvisible()

        createAccBtn.setOnClickListener {
            if (Helper.isInternetConnected(this)) {
                lifecycleScope.launch(Dispatchers.Main) {
                    registerUser()
                }
            } else {
                Toast.makeText(applicationContext, "Please Connect to WIFI !", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private suspend fun registerUser() {
        Log.i(TAG, "registerUser: clicked done")
        setProgressBarVisible()
        if (verifyInputs()) {
            Log.i(TAG, "registerUser: verified")
            if (!checkForSpecialChar()) {
                if (!checkChildExists()) {
                    Log.i(TAG, "registerUser: done")
                    uploadDataToFirebase()
                } else {
                    Toast.makeText(this, "User Already Registered!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this@RegisterActivity, "Fill properly...", Toast.LENGTH_LONG).show()
            }
        } else {
            Log.i(TAG, "registerUser: empty attributes found")
            Toast.makeText(this@RegisterActivity, "Fill properly...", Toast.LENGTH_LONG).show()
        }
        setProgressBarInvisible()
    }


    private fun uploadDataToFirebase() {
        val user = hashMapOf(
            "name" to firstNameET.text.trim().toString(),
            "userId" to userIdET.text.trim().toString(),
            "password" to passwordET.text.trim().toString(),
            "age" to 0,
        )
        val documentRef = db.collection("users").document(userIdET.text?.trim().toString())
        documentRef.set(user)
            .addOnSuccessListener {
                SharedPref.getInstance(this).setIsLoggedIn(true)
                SharedPref.getInstance(this).setUserName(userIdET.text.trim().toString())
                val intent = Intent(this, NavDrawerActivity::class.java)
                startActivity(intent)
                println("Document added with ID: ${userIdET.text?.trim().toString()}")
            }
            .addOnFailureListener { e ->
                println("Error adding document: $e")
            }
    }

    private fun setProgressBarVisible() {
        progressBar.visibility = View.VISIBLE
        createAccBtn.text = ""
    }

    private fun setProgressBarInvisible() {
        progressBar.visibility = View.GONE
        createAccBtn.text = "Create account"
    }

    fun onLoginClick(view: View) {
        finish()
    }

    private suspend fun checkChildExists(): Boolean = suspendCancellableCoroutine { continuation ->
        val documentRef = db.collection("users").document(userIdET.text?.trim().toString())
        documentRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    continuation.resumeWith(Result.success(document.exists()))
                } else {
                    continuation.resumeWith(Result.success(false))
                }
            } else {
                Log.d("TAG", "Error: ", task.exception)
                continuation.resumeWith(Result.success(false))
            }
        }

    }

    private fun checkForSpecialChar(): Boolean {
        val inputString = userIdET.text.toString()
        val pattern = Pattern.compile("[^a-zA-Z0-9]")
        val matcher = pattern.matcher(inputString)
        val isStringContainsSpecialCharacter = matcher.find()
        return if (isStringContainsSpecialCharacter) {
            Toast.makeText(
                this@RegisterActivity,
                "special character not allowed in UserID",
                Toast.LENGTH_SHORT
            ).show()
            true
        } else {
            false
        }
    }

    private fun verifyInputs(): Boolean {
        if (firstNameET.text.toString().isEmpty()) {
            return false
        }
        if (userIdET.text.toString().isEmpty()) {
            return false
        }
        if (passwordET.text.toString().isEmpty()) {
            return false
        }
        return true
    }

    private fun changeStatusBarColor() {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.statusBarColor = resources.getColor(R.color.register_bk_color)
    }
}