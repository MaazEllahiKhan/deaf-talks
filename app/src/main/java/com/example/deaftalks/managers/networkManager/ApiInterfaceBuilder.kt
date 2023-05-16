import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiInterfaceBuilder {
    companion object
    {
        var baseUrl= "BASE_URL"
      private  var apiInterFace: ApiInterface? = null
      private  var okHttpClient: OkHttpClient? = null

        fun getApiInterface(): ApiInterface?
        {
            if (apiInterFace == null) {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                //For printing API url and body in logcat
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

                okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(50, TimeUnit.SECONDS)
                    .writeTimeout(50, TimeUnit.SECONDS)
                    .callTimeout(50,TimeUnit.SECONDS)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()
                apiInterFace =retrofit.create(ApiInterface::class.java)
            }

            return apiInterFace
        }
    }
}