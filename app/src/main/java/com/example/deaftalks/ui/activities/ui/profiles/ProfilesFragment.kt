package com.example.deaftalks.ui.activities.ui.profiles

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deaftalks.databinding.FragmentProfilesBinding
import com.example.deaftalks.models.ProfileModel
import com.example.deaftalks.ui.activities.ui.ProfileDetailActivity
import com.example.deaftalks.ui.adapters.ProfileListAdapter
import com.example.deaftalks.ui.interfaces.OnItemClickListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson

class ProfilesFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentProfilesBinding? = null

    private val binding get() = _binding!!

    private val fireStore = FirebaseFirestore.getInstance()
    private var profileList: MutableList<ProfileModel> = ArrayList()
    private lateinit var adapter: ProfileListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewer.layoutManager = LinearLayoutManager(requireContext())
        getFireStoreDocuments()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFireStoreDocuments() {
        binding.progressBar.visibility = VISIBLE
        val collectionRef = fireStore.collection("interpreters")

        collectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    binding.progressBar.visibility = VISIBLE
                    val documentId = document.id
                    val data = document.data

                    // Access individual field values
                    val name = data["name"] as String
                    val expertise = data["expertise"] as String
                    val imageName = data["imageName"] as String
                    val description = data["description"] as String
                    val price = data["price"] as String
                    val monthly = data["monthly"] as String
                    val yearly = data["yearly"] as String
                    val age = data["age"] as String
                    val ethnicity = data["ethnicity"] as String


                    // Print the document details
                    Log.d(TAG, "Document ID: $documentId")
                    Log.d(TAG, "name 1: $name")
                    Log.d(TAG, "expertise: $expertise")
                    Log.d(TAG, "imageName: $imageName")
                    Log.d(TAG, "description: $description")
                    Log.d(TAG, "price: $price")

                    val storageRef = FirebaseStorage.getInstance().reference
                    val imagesRef = storageRef.child("images/$imageName")

                    val ONE_MEGABYTE = 1024 * 1024.toLong()
                    imagesRef.getBytes(ONE_MEGABYTE)
                        .addOnSuccessListener { imageData ->
                            val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                            profileList.add(
                                ProfileModel(
                                    username = name,
                                    expertise = expertise,
                                    imageName = imageName,
                                    description = description,
                                    price = price,
                                    profileImage = bitmap,
                                    monthly = monthly,
                                    yearly = yearly,
                                    age = age,
                                    ethnicity = ethnicity,
                                )
                            )
                            adapter.notifyDataSetChanged()
                            binding.progressBar.visibility = GONE
                            Log.i(TAG, "onViewCreated: image $bitmap")
                        }
                        .addOnFailureListener { exception ->
                            Log.i(TAG, "onViewCreated: image failed")
                            exception.printStackTrace()
                        }


                }
                adapter = ProfileListAdapter(profileList, this)
                binding.recyclerViewer.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting FireStore documents: ", exception)
            }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onItemClick(position: Int) {
        val customModel = profileList[position]
        val gson = Gson()
        val json = gson.toJson(customModel)
        val intent = Intent(requireContext(), ProfileDetailActivity::class.java)
        intent.putExtra("profileModelModel", json)
        startActivity(intent)
    }
}
