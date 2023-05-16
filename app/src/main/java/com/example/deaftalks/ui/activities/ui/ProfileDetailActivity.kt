package com.example.deaftalks.ui.activities.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.deaftalks.R
import com.example.deaftalks.databinding.ActivityProfileDetailBinding
import com.example.deaftalks.databinding.FragmentAddInterceptorBinding
import com.example.deaftalks.models.ProfileModel
import com.google.gson.Gson

class ProfileDetailActivity : AppCompatActivity() {
    private  val TAG="ProfileDetailPage"
    private lateinit var profileModel: ProfileModel
    val gson = Gson()
    private var binding: ActivityProfileDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        val intent = intent
        val profileModelJson = intent.getStringExtra("profileModelModel")
        val personModelFromJson = gson.fromJson(profileModelJson,ProfileModel::class.java)
        Log.i(TAG, "onCreate: Profile Page "+personModelFromJson.username)


        setupUi(personModelFromJson)
    }

    private fun setupUi(personModelFromJson: ProfileModel) {
        binding?.userProfileIV?.setImageBitmap(personModelFromJson.profileImage)
        binding?.usernameTV?.text= personModelFromJson.username
        binding?.expertiseTV?.text= personModelFromJson.expertise
        binding?.ageTV?.text= personModelFromJson.age
        binding?.ethnicityTV?.text= personModelFromJson.ethnicity
        binding?.descriptionTV?.text= personModelFromJson.description
        binding?.hireMeButton?.text=personModelFromJson.price + "$ Hourly"
        binding?.hireMeButtonMonthly?.text= personModelFromJson.monthly+ "$ Monthly"
        binding?.hireMeButtonYearly?.text= personModelFromJson.yearly+ "$ Yearly"
    }
}