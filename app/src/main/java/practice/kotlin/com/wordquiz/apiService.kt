package practice.kotlin.com.wordquiz

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.Query

interface apiService {
    @Multipart
    @GET("/toeic")
    fun getUser(@Query("korean") korean : String?) : Observable<JsonObject>

}