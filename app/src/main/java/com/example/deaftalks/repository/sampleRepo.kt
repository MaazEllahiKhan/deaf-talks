
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.managers.networkManager.weatherAPI.SampleManager
import com.example.weatherapp.utlis.Enum

object sampleRepo {
    const val TAG="SampleRepo"
    private var instance: sampleRepo? = null

    fun getInstance(): sampleRepo {
        if (instance == null) {
            instance = sampleRepo
        }
        return instance!!
    }


}
