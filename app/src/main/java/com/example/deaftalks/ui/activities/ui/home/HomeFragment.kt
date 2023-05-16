package com.example.deaftalks.ui.activities.ui.home
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.deaftalks.databinding.FragmentHomeBinding
import com.example.deaftalks.ui.interfaces.BatteryCallback
import com.example.deaftalks.utlis.BatteryReceiver
import java.util.Objects

class HomeFragment : Fragment() ,BatteryCallback{

    private var _binding: FragmentHomeBinding? = null

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
        batteryReceiver = BatteryReceiver(callback =this)
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        requireContext().registerReceiver(batteryReceiver, filter)
        setupSlider()

    }

    private fun setupSlider() {
// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel("https://bit.ly/2YoJ77H", "The animal population decreased by 58 percent in 42 years."))
        imageList.add(SlideModel("https://bit.ly/2BteuF2", "Elephants and tigers may become extinct."))
        imageList.add(SlideModel("https://bit.ly/3fLJf72", "And people do that."))

        binding.imageSlider.setImageList(imageList,ScaleTypes.FIT)
        Log.i("TAG", "setupSlider: ")
        Log.i("TAG", "setupSlider: ")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onBatteryPercentageUpdated(percentage: Int) {
//        binding.batteryTV.text ="Battery Percentage: $percentage%"
//        batteryReceiver?.let {
//            requireContext().unregisterReceiver(it)
//            batteryReceiver = null
//        }
    }

    override fun onBatteryChargingStatusUpdated(isCharging: Boolean) {

    }
}