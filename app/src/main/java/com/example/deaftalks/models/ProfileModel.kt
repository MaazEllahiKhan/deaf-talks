package com.example.deaftalks.models

import android.graphics.Bitmap

data class ProfileModel(
    var username :String,
    var expertise :String,
    var description :String,
    var price:String,
    var profileImage: Bitmap,
    var imageName:String,
    var monthly: String,
    var yearly:String,
    var age:String,
    var ethnicity:String
)
