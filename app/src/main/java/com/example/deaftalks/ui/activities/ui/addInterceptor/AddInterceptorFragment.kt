package com.example.deaftalks.ui.activities.ui.addInterceptor

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.deaftalks.R
import com.example.deaftalks.databinding.FragmentAddInterceptorBinding
import com.example.deaftalks.ui.activities.NavDrawerActivity
import com.example.deaftalks.utlis.Helper
import com.example.deaftalks.utlis.SharedPref
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.*
import java.util.*


class AddInterceptorFragment : Fragment() {

    private var _binding: FragmentAddInterceptorBinding? = null
    private val TAG = "AddInterceptor"
    private val binding get() = _binding!!
    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private val STORAGE_PERMISSION_REQUEST_CODE = 101
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2
    private lateinit var viewFinder: TextureView
    private  lateinit var bitmap:Bitmap
    var db = FirebaseFirestore.getInstance()
    var vFilename: String = ""
    var expertise:String = ""
    var ethnicity:String = ""
    private lateinit var imageName:String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddInterceptorBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uploadImgIV.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }

        }

        val names = resources.getStringArray(R.array.expertise)
        val ethnicities = resources.getStringArray(R.array.ethnicities)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, names)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.nameSpinner.adapter = adapter

        binding.nameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                expertise= parent.getItemAtPosition(position) as String

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        binding.ethnicity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                ethnicity= parent.getItemAtPosition(position) as String

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        binding.addBtn.setOnClickListener {
            if(Helper.isInternetConnected(requireContext())) {
                addButtonClicked()
            }else{
                Toast.makeText(requireContext(),"Please Connect to WIFI !",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openCamera() {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    openCamera()
                } else {
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_LONG).show()
                }
            }
            STORAGE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult: if")
                    //  openCamera()
                } else {
                    Log.i(TAG, "onRequestPermissionsResult: else")
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    bitmap = data?.extras?.get("data") as Bitmap
                    binding.profileImg.setImageBitmap(getCircleBitmap(bitmap))
                    binding.uploadImgIV.visibility= GONE
                    binding.profileImg.visibility= VISIBLE
                    binding.hourlyRateET.text.clear()
                    binding.descriptionET.text.clear()
                }
                GALLERY_REQUEST_CODE -> {
                   // binding.imageView.setImageURI(data?.data)
                }
            }
        }
    }

    private fun getCircleBitmap(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(outputBitmap)

        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val radius = Math.min(width, height) / 2.toFloat()
        canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), radius, paint)

        return outputBitmap
    }

    private fun verifyInput(): Boolean {
        if(binding.nameET.text.trim().toString().isEmpty()){
            return false
        }
        if(binding.descriptionET.text.trim().toString().isEmpty()){
            return false
        }
        if(binding.hourlyRateET.text.trim().toString().isEmpty()){
            return false
        }
        if(binding.monthlyRateET.text.trim().toString().isEmpty()){
            return false
        }
        if(binding.yearlyRateET.text.trim().toString().isEmpty()){
            return false
        }
        if(binding.ageET.text.trim().toString().isEmpty()){
            return false
        }
        return true
    }

    private fun addButtonClicked(){
        if(verifyInput()){
            binding.progressBar.visibility= VISIBLE
            uploadBitmapToFirebase()
        }

    }

    private fun uploadInterpreterData() {
        val user = hashMapOf(
            "name" to binding.nameET.text.trim().toString(),
            "expertise" to expertise,
            "imageName" to imageName,
            "description" to binding.descriptionET.text.trim().toString(),
            "price" to binding.hourlyRateET.text.trim().toString(),
            "monthly" to binding.monthlyRateET.text.trim().toString(),
            "yearly" to binding.yearlyRateET.text.trim().toString(),
            "age" to binding.ageET.text.trim().toString(),
            "ethnicity" to ethnicity,
            "admin" to SharedPref.getInstance(requireContext()).getUserName()
        )

        val documentRef = db.collection("interpreters").document(binding.nameET.text.trim().toString())
        documentRef.set(user)
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Interpreter added successfully..!",Toast.LENGTH_LONG).show()
                imageName=""
                binding.uploadImgIV.visibility = VISIBLE
                binding.profileImg.visibility = GONE
                binding.nameET.text.clear()
                binding.hourlyRateET.text.clear()
                binding.monthlyRateET.text.clear()
                binding.yearlyRateET.text.clear()
                binding.descriptionET.text.clear()
                binding.ageET.text.clear()
                binding.progressBar.visibility= GONE

            }
            .addOnFailureListener { e ->
                binding.progressBar.visibility= GONE
                println("Error adding document: $e")
            }
    }

    private fun uploadBitmapToFirebase() {
        binding.progressBar.visibility= VISIBLE
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef = storageRef.child("images")

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        imageName = "image_${System.currentTimeMillis()}.jpg"
        val imageRef = imagesRef.child(imageName)

        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnSuccessListener {
            uploadInterpreterData()
        }.addOnFailureListener {
        }
    }

}