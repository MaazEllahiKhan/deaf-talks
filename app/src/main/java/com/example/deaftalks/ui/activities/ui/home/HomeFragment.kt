package com.example.deaftalks.ui.activities.ui.home

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.deaftalks.R
import com.example.deaftalks.databinding.FragmentHomeBinding
import com.example.deaftalks.ui.interfaces.BatteryCallback
import com.example.deaftalks.utlis.BatteryReceiver


class HomeFragment : Fragment() ,BatteryCallback{

    private var _binding: FragmentHomeBinding? = null
    private lateinit var  navController: NavController
    private val binding get() = _binding!!
    private var batteryReceiver: BatteryReceiver? = null
    val imageList = ArrayList<SlideModel>() // Create image list

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     //   batteryReceiver = BatteryReceiver(callback =this)
        navController = Navigation.findNavController(requireView())
        if(!imageList.isEmpty()){
         //   navController.navigate(R.id.nav_home)
        }
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
       // requireContext().registerReceiver(batteryReceiver, filter)
        setupSlider()
        binding.findInterpreterBtn.setOnClickListener {
            navController.navigate(R.id.Profiles)

        }

    }

    private fun setupSlider() {
        Log.i("TAG", "setupSlider: imageList size "+imageList.size)
        if (imageList.isEmpty()) {
            imageList.add(
                SlideModel(
                    R.drawable.bg_deaf_slider_1,
                    "Deaf signs are visual gestures used by individuals who are deaf or hard of hearing to communicate."
                )
            )
            imageList.add(
                SlideModel(
                    R.drawable.bg_deaf_slider_2,
                    "Hearing refers to the ability to perceive and interpret sound through the auditory system. "
                )
            )
            imageList.add(SlideModel("https://bit.ly/3fLJf72", "Despite facing unique challenges, you have shown that communication knows no bounds"))
            imageList.add(SlideModel(R.drawable.bg_deaf_slider_image, "And people do that."))

        }
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onBatteryPercentageUpdated(percentage: Int) {

    }

    override fun onBatteryChargingStatusUpdated(isCharging: Boolean) {

    }
}