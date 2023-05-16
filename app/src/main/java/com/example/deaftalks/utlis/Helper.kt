package com.example.deaftalks.utlis
import android.content.Context
import android.graphics.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine


class Helper {
    companion object {
        fun isInternetConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                else -> false
            }
        }
        suspend fun getImageFromFireStore(): Bitmap = suspendCancellableCoroutine {

        }

        // Create an empty Bitmap with specified width and height
        fun createEmptyBitmap(width: Int, height: Int): Bitmap {
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }

        fun getCircleBitmap(bitmap: Bitmap): Bitmap {
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




    }

}